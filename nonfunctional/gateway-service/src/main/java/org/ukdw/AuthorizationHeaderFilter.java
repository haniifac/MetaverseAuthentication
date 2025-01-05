package org.ukdw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.ukdw.common.ResponseWrapper;
import org.ukdw.common.dto.VerifyTokenDto;
import reactor.core.publisher.Mono;


@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationHeaderFilter.class);
    private final WebClient.Builder webClientBuilder;
    private final LoadBalancerClient loadBalancerClient;

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

//    @Value("${service.user.url.base}")
//    private String authServiceUrl;
//    private String authServiceUrl = resolveServiceUrl();

    public AuthorizationHeaderFilter(WebClient.Builder webClientBuilder, LoadBalancerClient loadBalancerClient) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
        this.loadBalancerClient = loadBalancerClient;
    }

    public static class Config {
        // Put configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "").trim();

            return verifyTokenWithUserService(jwt)
                    .flatMap(verifyTokenDto -> {
                        if (verifyTokenDto == null) {
                            return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
                        }

                        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                .header("X-role", verifyTokenDto.getRole())
                                .header("X-permission", String.valueOf(verifyTokenDto.getPermission()))
                                .header("X-id", String.valueOf(verifyTokenDto.getId()))
                                .build();

                        return chain.filter(exchange.mutate().request(modifiedRequest).build());

                    }).onErrorResume(e -> {
                        if (e instanceof RuntimeException) {
                            String message = e.getMessage();
                            if ("Client Error".equals(message)) {
                                return onError(exchange, "Unauthorized", HttpStatus.UNAUTHORIZED);
                            } else if ("Server Error".equals(message)) {
                                return onError(exchange, "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                        }
                        return onError(exchange, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
                    });
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
//        ResponseWrapper errorResponse = new ResponseWrapper();
//        errorResponse.setMessage(err);
//        errorResponse.setStatus(httpStatus.value());

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();

//        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponse.toString().getBytes())));
    }


    @LoadBalanced
    private Mono<VerifyTokenDto> verifyTokenWithUserService(String token) {
        String authServiceUrl = resolveServiceUrl("auth-service"); // Resolve the service URL dynamically

        return webClientBuilder.build()
                .get()
                .uri(authServiceUrl + "/auth/verify")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
//                    log.error("4xx Client Error: {}", clientResponse.statusCode());
                    clientResponse.bodyToMono(String.class).doOnTerminate(() -> {
                        log.error("4xx Client Error: {} - Response Body: {}", clientResponse.statusCode(), clientResponse.toString());
                    }).subscribe();
                    return Mono.error(new RuntimeException("Client Error"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
//                    log.error("5xx Server Error: {}", clientResponse.statusCode());
                    clientResponse.bodyToMono(String.class).doOnTerminate(() -> {
                        log.error("5xx Server Error: {} - Response Body: {}", clientResponse.statusCode(), clientResponse.toString());
                    }).subscribe();
                    return Mono.error(new RuntimeException("Server Error"));
                })
                .bodyToMono(new ParameterizedTypeReference<ResponseWrapper<VerifyTokenDto>>() {})
                .handle((responseWrapper, sink) -> {
//                    log.info("RESPONSE :{}", responseWrapper.getData().toString());
                    log.error("Token verification status: {}", responseWrapper.getStatus());
                    if (responseWrapper.getStatus() == 200) {
                        sink.next(responseWrapper.getData());
                    } else {
                        sink.error(new WebClientResponseException(responseWrapper.getMessage(), responseWrapper.getStatus(), null, null, null, null));
                    }
                });
    }

    private String resolveServiceUrl(String serviceName) {
        var serviceInstance = loadBalancerClient.choose(serviceName);
        if (serviceInstance != null) {
            log.info(serviceInstance.getUri().toString());
            return serviceInstance.getUri().toString();
        } else {
            throw new IllegalStateException("Service not available: " + serviceName);
        }
    }
}

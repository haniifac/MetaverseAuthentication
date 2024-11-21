package org.ukdw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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

    private final WebClient.Builder webClientBuilder;

    public AuthorizationHeaderFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    public static class Config {
        // Put configuration properties here
    }


    @Value("${service.user.url.base}")
    private String userServiceUrl;

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
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }


    @LoadBalanced
    private Mono<VerifyTokenDto> verifyTokenWithUserService(String token) {
        return webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "/verify")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Client Error")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(new ParameterizedTypeReference<ResponseWrapper<VerifyTokenDto>>() {})
                .handle((responseWrapper, sink) -> {
                    if (responseWrapper.getStatus() == 200) {
                        sink.next(responseWrapper.getData());
                    } else {
                        sink.error(new WebClientResponseException(responseWrapper.getMessage(), responseWrapper.getStatus(), null, null, null, null));
                    }
                });
    }
}

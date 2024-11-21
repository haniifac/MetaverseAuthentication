package org.ukdw.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

    @Value("${http.outgoing.read-timeout-common}")
    private int readTimeoutCommon;

    @Value("${http.outgoing.connection-timeout-common}")
    private int connectionTimeoutCommon;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateCommon() {
        return new RestTemplateBuilder()
                .setReadTimeout(Duration.ofMillis(readTimeoutCommon))
                .setConnectTimeout(Duration.ofMillis(connectionTimeoutCommon))
                .build();
    }
}

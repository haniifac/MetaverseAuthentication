package org.ukdw.classroom.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfigurationClassroom {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setReadTimeout(Duration.ofMillis(120000))
                .setConnectTimeout(Duration.ofMillis(120000))
                .build();
    }
}

package com.WalkiePaw.presentation.domain.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

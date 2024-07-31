package com.team29.ArtifactV2.clova.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ClovaConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

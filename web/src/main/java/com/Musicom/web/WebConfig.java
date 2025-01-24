package com.Musicom.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WebConfig {
    @Bean
    public RestClient restClient(@Value("${api}") String api) {
        return RestClient.create(api);
    }
}

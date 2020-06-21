package com.egemsoft.rickandmorty.config;

import com.egemsoft.rickandmorty.service.ReportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {

    @Bean("apiClient")
    public RestTemplate apiClient() {
        return new RestTemplate();
    }

}

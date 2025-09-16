package com.openclassrooms.medilabo.patientui.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Base64;

@Configuration
public class FeignAuthConfig {

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return template -> {
            String username = "admin";
            String password = "admin";
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            template.header("Authorization", "Basic " + encodedAuth);
        };
    }
}

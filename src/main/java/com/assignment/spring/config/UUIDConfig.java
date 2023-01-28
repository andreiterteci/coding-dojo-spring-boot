package com.assignment.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class UUIDConfig {
    @Bean
    public UUIDGenerator uuidBean() {
        return () -> UUID.randomUUID().toString();
    }
}

package com.assignment.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.util.TimeZone;

@Configuration
public class ClockConfig {

    @Bean
    public Clock getClock() {
        return Clock.systemUTC();
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}

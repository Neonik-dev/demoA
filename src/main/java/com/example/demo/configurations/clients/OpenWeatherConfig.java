package com.example.demo.configurations.clients;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client.openweather")
public class OpenWeatherConfig {
    private String baseUrl;
    private String appId;
}
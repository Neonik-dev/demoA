package com.example.demo.clients.clients;

import com.example.demo.configurations.clients.OpenWeatherConfig;
import com.example.demo.dto.Coordinates;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component(value = "OpenWeather")
public class OpenWeatherClient implements IOuterSiteClient {
    private static final String PARAM_APP_ID = "appid";
    private static final String PARAM_LAT = "lat";
    private static final String PARAM_LON = "lon";
    private static final ParameterizedTypeReference<Map<String, String>> MAP_RESPONSE =
            new ParameterizedTypeReference<>() {};

    private final WebClient webClient;
    private final OpenWeatherConfig config;

    public OpenWeatherClient(OpenWeatherConfig openWeatherConfig) {
        this.config = openWeatherConfig;
        webClient = WebClient.create(openWeatherConfig.getBaseUrl());
    }

    @Override
    public Mono<Map<String, String>> getInfoByCoordinates(Coordinates coordinates) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam(PARAM_LAT, coordinates.latitude())
                        .queryParam(PARAM_LON, coordinates.longitude())
                        .queryParam(PARAM_APP_ID, config.getAppId())
                        .build()
                ).retrieve().bodyToMono(MAP_RESPONSE);
    }
}

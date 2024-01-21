package com.example.demo.clients.clients;

import com.example.demo.configurations.clients.GismeteoConfig;
import com.example.demo.dto.Coordinates;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component(value = "Gismeteo")
public class GismeteoClient implements IOuterSiteClient {
    private static final String HEADER_GISMETEO_TOKEN = "X-Gismeteo-Token";
    private static final String PARAM_LAT = "latitude";
    private static final String PARAM_LON = "longitude";
    private static final ParameterizedTypeReference<Map<String, String>> MAP_RESPONSE =
            new ParameterizedTypeReference<>() {};

    private final WebClient webClient;
    private final GismeteoConfig config;

    public GismeteoClient(GismeteoConfig gismeteoConfig) {
        this.config = gismeteoConfig;
        webClient = WebClient.create(gismeteoConfig.getBaseUrl());
    }

    @Override
    public Map<String, String> getInfoByCoordinates(Coordinates coordinates) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/")
                        .queryParam(PARAM_LAT, coordinates.latitude())
                        .queryParam(PARAM_LON, coordinates.longitude())
                        .build()
                ).header(HEADER_GISMETEO_TOKEN, config.getToken())
                .retrieve().bodyToMono(MAP_RESPONSE).block();
    }
}

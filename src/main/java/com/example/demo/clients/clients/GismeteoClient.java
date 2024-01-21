package com.example.demo.clients.clients;

import com.example.demo.configurations.clients.GismeteoConfig;
import com.example.demo.dto.Coordinates;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class GismeteoClient implements IOuterSiteClient {
    private static final String HEADER_GISMETEO_TOKEN = "X-Gismeteo-Token";
    private static final String GISMETEO_TOKEN = "56b30cb255.3443075";
    private static final ParameterizedTypeReference<Map<String, String>> MAP_RESPONSE =
            new ParameterizedTypeReference<>() {};

    private final WebClient webClient;

    public GismeteoClient(GismeteoConfig gismeteoConfig) {
        webClient = WebClient.create(gismeteoConfig.getBaseUrl());
    }

    @Override
    public Map<String, String> getInfoByCoordinates(Coordinates coordinates) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/")
                        .queryParam("latitude", coordinates.latitude())
                        .queryParam("longitude", coordinates.longitude())
                        .build()
                )
                .header(HEADER_GISMETEO_TOKEN, GISMETEO_TOKEN)
                .retrieve().bodyToMono(MAP_RESPONSE).block();
    }
}

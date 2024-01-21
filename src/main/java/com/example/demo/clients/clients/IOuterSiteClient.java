package com.example.demo.clients.clients;

import com.example.demo.dto.Coordinates;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface IOuterSiteClient {
    Mono<Map<String, String>> getInfoByCoordinates(Coordinates coordinates);
}

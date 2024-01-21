package com.example.demo.clients.clients;

import com.example.demo.dto.Coordinates;

import java.util.Map;

public interface IOuterSiteClient {
    Map<String, String> getInfoByCoordinates(Coordinates coordinates);
}

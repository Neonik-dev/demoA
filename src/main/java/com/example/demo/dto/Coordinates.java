package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record Coordinates(
        @NotBlank
        String latitude,
        @NotBlank
        String longitude
) {
}

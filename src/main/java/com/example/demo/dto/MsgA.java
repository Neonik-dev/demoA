package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MsgA(
        @NotBlank
        String msg,
        @NotNull
        String lng,
        @NotNull
        Coordinates coordinates
) {
}

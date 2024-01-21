package com.example.demo.clients.dto;

import java.time.LocalDateTime;

public record MsgB(
        String txt,
        LocalDateTime createdDt,
        Integer currentTemp
) {
}

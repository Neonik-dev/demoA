package com.example.demo.clients.clients;

import com.example.demo.clients.dto.MsgB;
import com.example.demo.configurations.clients.BConfig;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BClient {
    private final WebClient webClient;

    public BClient(BConfig bConfig) {
        webClient = WebClient.create(bConfig.getBaseUrl());
    }

    public void sendMsg(MsgB msgB) {
        System.out.println(msgB);
        webClient.post().uri("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(msgB)
                .retrieve().bodyToMono(Void.class).block();
        System.out.println("good");
    }
}

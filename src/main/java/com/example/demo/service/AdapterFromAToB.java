package com.example.demo.service;

import com.example.demo.clients.clients.BClient;
import com.example.demo.clients.clients.IOuterSiteClient;
import com.example.demo.clients.dto.MsgB;
import com.example.demo.dto.LngEnum;
import com.example.demo.dto.MsgA;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AdapterFromAToB implements IAdapterService {
    private final BClient bClient;
    @Qualifier(value = "Gismeteo")
    private final IOuterSiteClient gismeteoClient;
    @Qualifier(value = "OpenWeather")
    private final IOuterSiteClient openWeatherClient;

    public void adapter(MsgA msgA) {
        if (!isValid(msgA)) {
            return;
        }


        sendToB(new MsgB("4",  LocalDateTime.now(), 4));
    }

    private MsgB formingMsgB(MsgA msgA) {
        Mono<Map<String, String>> gismeteoResponse = gismeteoClient.getInfoByCoordinates(msgA.coordinates());
        Mono<Map<String, String>> openWeatherResponse = openWeatherClient.getInfoByCoordinates(msgA.coordinates());

        return Mono.zip(
                gismeteoResponse,
                openWeatherResponse,
                (person, hobbies) -> new MsgB(
                        msgA.msg(),
                        LocalDateTime.now(),
                        2
                )
        ).block();
    }

    private boolean isValid(MsgA msgA) {
        return LngEnum.fromString(msgA.lng()) == LngEnum.RU;
    }

    private void sendToB(MsgB msgB) {
        bClient.sendMsg(msgB);
    }
}

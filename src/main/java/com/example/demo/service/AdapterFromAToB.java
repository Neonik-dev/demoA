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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AdapterFromAToB implements IAdapterService {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'h:m:ss.ssXXX");
    private final BClient bClient;
    @Qualifier(value = "Gismeteo")
    private final IOuterSiteClient gismeteoClient;
    @Qualifier(value = "OpenWeather")
    private final IOuterSiteClient openWeatherClient;

    public void adapter(MsgA msgA) {
        if (!isValid(msgA)) {
            return;
        }
        MsgB msgB = formingMsgB(msgA);
        sendToB(msgB);
    }

    private MsgB formingMsgB(MsgA msgA) {
        Mono<Map<String, String>> gismeteoResponse = gismeteoClient.getInfoByCoordinates(msgA.coordinates());
        Mono<Map<String, String>> openWeatherResponse = openWeatherClient.getInfoByCoordinates(msgA.coordinates());

        return Mono.zip(
                gismeteoResponse,
                openWeatherResponse,
                (gismeteo, openWeather) -> new MsgB(
                        msgA.msg(),
                        DATE_FORMAT.format(new Date()),
                        (
                                Integer.parseInt(gismeteo.get("temperature"))
                                        + Integer.parseInt(openWeather.get("temperature"))
                        ) / 2
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

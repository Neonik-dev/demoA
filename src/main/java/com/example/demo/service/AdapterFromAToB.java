package com.example.demo.service;

import com.example.demo.clients.clients.BClient;
import com.example.demo.clients.clients.GismeteoClient;
import com.example.demo.clients.dto.MsgB;
import com.example.demo.dto.LngEnum;
import com.example.demo.dto.MsgA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdapterFromAToB implements IAdapterService {
    private final BClient bClient;
    private final GismeteoClient gismeteoClient;

    public void adapter(MsgA msgA) {
        if (!isValid(msgA)) {
            return;
        }
        gismeteoClient.getInfoByCoordinates(msgA.coordinates());

        sendToB(new MsgB("4",  LocalDateTime.now(), 4));
    }

    private boolean isValid(MsgA msgA) {
        return LngEnum.fromString(msgA.lng()) == LngEnum.RU;
    }

    private void sendToB(MsgB msgB) {
        bClient.sendMsg(msgB);
    }
}

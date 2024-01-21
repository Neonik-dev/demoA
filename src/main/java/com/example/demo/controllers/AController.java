package com.example.demo.controllers;

import com.example.demo.dto.MsgA;
import com.example.demo.service.IAdapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AController {
    private final IAdapterService adapterService;

    @PostMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    public void postMessage(@RequestBody @Valid MsgA msgA) {
        adapterService.adapter(msgA);
    }
}

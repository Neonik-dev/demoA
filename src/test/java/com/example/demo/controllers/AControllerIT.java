package com.example.demo.controllers;


import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AControllerIT {
    @Autowired
    private MockMvc mvc;

    @Test
    public void sendEmptyMsg_400() throws Exception {
        // given
        String contentMsgA = """
                {
                    "msg": "",
                    "lng": "ru",
                    "coordinates": {
                        "latitude": "10",
                        "longitude": "11"
                    }
                }""";

        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/api/message")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(contentMsgA)
                ).andExpect(status().isBadRequest())
                .andReturn().getResponse();

        // then
        String description = JsonPath.read(response.getContentAsString(), "$.description");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertEquals(description, "The message cannot be empty");
    }

    @Test
    public void sendLngEN_ignore() throws Exception {
        // given
        String contentMsgA = """
                {
                    "msg": "HiHiHI",
                    "lng": "en",
                    "coordinates": {
                        "latitude": "10",
                        "longitude": "11"
                    }
                }""";

        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/api/message")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(contentMsgA)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void sendBadLng_400() throws Exception {
        // given
        String contentMsgA = """
                {
                    "msg": "HiHiHI",
                    "lng": "r",
                    "coordinates": {
                        "latitude": "10",
                        "longitude": "11"
                    }
                }""";

        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/api/message")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(contentMsgA)
                ).andExpect(status().isBadRequest())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
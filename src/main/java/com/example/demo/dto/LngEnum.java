package com.example.demo.dto;

import lombok.Getter;

@Getter
public enum LngEnum {
    RU("ru"),
    EN("en"),
    ES("es");

    private final String name;

    LngEnum(String name) {
        this.name = name;
    }

    public static LngEnum fromString(String name) {
        for (LngEnum lng : LngEnum.values()) {
            if (name.equals(lng.name)) {
                return lng;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}

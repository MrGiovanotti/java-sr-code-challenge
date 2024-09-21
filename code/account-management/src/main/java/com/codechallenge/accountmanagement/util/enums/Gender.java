package com.codechallenge.accountmanagement.util.enums;

import lombok.Getter;

@Getter
public enum Gender {

    MALE("Masculino"),
    FEMALE("Femenino");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

}

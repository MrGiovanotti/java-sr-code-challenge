package com.codechallenge.customermanagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Error implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;

}

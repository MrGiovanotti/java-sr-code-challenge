package com.codechallenge.accountmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CustomError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;

}

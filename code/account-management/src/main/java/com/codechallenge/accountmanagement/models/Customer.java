package com.codechallenge.accountmanagement.models;

import com.codechallenge.accountmanagement.util.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Gender gender;
    private int age;
    private String identificationNumber;
    private String address;
    private String phone;
    private boolean status;
}

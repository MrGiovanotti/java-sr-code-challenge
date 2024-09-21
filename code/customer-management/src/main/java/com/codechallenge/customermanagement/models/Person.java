package com.codechallenge.customermanagement.models;

import com.codechallenge.customermanagement.util.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private Gender gender;
    int age;

    @Column(name = "identification_number", unique = true)
    String identificationNumber;

    String address;
    String phone;

}

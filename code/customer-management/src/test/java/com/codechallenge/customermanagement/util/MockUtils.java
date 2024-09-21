package com.codechallenge.customermanagement.util;

import com.codechallenge.customermanagement.entities.Customer;
import com.codechallenge.customermanagement.util.enums.Gender;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockUtils {

    public static Customer mockCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Juan Perez");
        customer.setGender(Gender.MALE);
        customer.setAge(30);
        customer.setIdentificationNumber("1234567890");
        customer.setAddress("123 Main St");
        customer.setPhone("123-456-7890");
        customer.setPassword("secret");
        customer.setStatus(true);
        return customer;
    }

}

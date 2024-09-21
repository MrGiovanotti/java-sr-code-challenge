package com.codechallenge.customermanagement.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
    }

    @Test
    void testCustomerId() {
        customer.setId(1);
        assertEquals(1, customer.getId(), "El ID del cliente debe ser 1.");
    }

    @Test
    void testCustomerPassword() {
        customer.setPassword("secret");
        assertEquals("secret", customer.getPassword(), "La contraseña del cliente debe ser 'secret'.");
    }

    @Test
    void testCustomerStatus() {
        customer.setStatus(true);
        assertTrue(customer.isStatus(), "El estado del cliente debe ser true.");
    }

    @Test
    void testInheritanceFromPerson() {
        customer.setName("John Doe");
        assertEquals("John Doe", customer.getName(), "El nombre del cliente debe ser 'John Doe'.");
    }

    @Test
    void testPersonDetails() {
        customer.setAge(30);
        assertEquals(30, customer.getAge(), "La edad del cliente debe ser 30.");
    }

}
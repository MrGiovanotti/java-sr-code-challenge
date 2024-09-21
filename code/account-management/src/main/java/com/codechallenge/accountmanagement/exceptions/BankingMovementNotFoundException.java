package com.codechallenge.accountmanagement.exceptions;

public class BankingMovementNotFoundException extends RuntimeException {

    public BankingMovementNotFoundException(String message) {
        super(message);
    }
}

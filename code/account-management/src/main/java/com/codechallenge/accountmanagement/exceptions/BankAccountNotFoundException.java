package com.codechallenge.accountmanagement.exceptions;

public class BankAccountNotFoundException extends RuntimeException{

    public BankAccountNotFoundException(String message) {
        super(message);
    }

}

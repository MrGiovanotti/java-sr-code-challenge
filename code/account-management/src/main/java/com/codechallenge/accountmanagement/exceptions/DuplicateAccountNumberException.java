package com.codechallenge.accountmanagement.exceptions;

public class DuplicateAccountNumberException extends RuntimeException{

    public DuplicateAccountNumberException(String message) {
        super(message);
    }

}

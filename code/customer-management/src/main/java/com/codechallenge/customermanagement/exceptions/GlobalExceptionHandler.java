package com.codechallenge.customermanagement.exceptions;

import com.codechallenge.customermanagement.models.Error;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Error> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(exception.getMessage()));
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Error> handleGenericCustomerException(GenericException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(exception.getMessage()));
    }

    @ExceptionHandler(CustomerDuplicateIdentificationException.class)
    public ResponseEntity<Error> handleCustomerDuplicateIdentificationException(CustomerDuplicateIdentificationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error("Error general: " + exception.getMessage()));
    }

}

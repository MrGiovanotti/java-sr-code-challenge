package com.codechallenge.accountmanagement.exceptions;

import com.codechallenge.accountmanagement.models.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<CustomError> handleBankAccountNotFoundException(BankAccountNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomError(exception.getMessage()));
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<CustomError> handleGenericBankAccountException(GenericException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomError(exception.getMessage()));
    }

    @ExceptionHandler(DuplicateAccountNumberException.class)
    public ResponseEntity<CustomError> handleDuplicateAccountNumberException(DuplicateAccountNumberException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(exception.getMessage()));
    }

    @ExceptionHandler(IncorrectAmountException.class)
    public ResponseEntity<CustomError> handleIncorrectAmountException(IncorrectAmountException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(exception.getMessage()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<CustomError> handleInsufficientBalanceException(InsufficientBalanceException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(exception.getMessage()));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<CustomError> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomError(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomError("Error general: " + exception.getMessage()));
    }

}

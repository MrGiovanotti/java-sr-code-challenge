package com.codechallenge.accountmanagement.utils;

import com.codechallenge.accountmanagement.entities.BankAccount;
import com.codechallenge.accountmanagement.entities.BankingMovement;
import com.codechallenge.accountmanagement.models.Customer;
import com.codechallenge.accountmanagement.util.enums.AccountType;
import com.codechallenge.accountmanagement.util.enums.Gender;
import com.codechallenge.accountmanagement.util.enums.MovementType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockUtils {

    public static BankAccount mockBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);
        bankAccount.setAccountNumber("123456789");
        bankAccount.setAccountType(AccountType.CHECKING);
        bankAccount.setInitialBalance(new BigDecimal(1000));
        bankAccount.setTotalBalance(new BigDecimal(1000));
        bankAccount.setStatus(true);
        bankAccount.setCustomerId(1);
        return bankAccount;
    }

    public static Customer mockCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Juan Perez");
        customer.setGender(Gender.MALE);
        customer.setAge(30);
        customer.setIdentificationNumber("1234567890");
        customer.setAddress("123 Main St");
        customer.setPhone("123-456-7890");
        customer.setStatus(true);
        return customer;
    }

    public static BankingMovement mockBankingMovement() {
        BankingMovement bankingMovement = new BankingMovement();
        bankingMovement.setDate(new java.util.Date());
        bankingMovement.setAmount(new BigDecimal(1000));
        bankingMovement.setMovementType(MovementType.DEPOSIT);
        bankingMovement.setBankAccount(mockBankAccount());
        return bankingMovement;
    }

}

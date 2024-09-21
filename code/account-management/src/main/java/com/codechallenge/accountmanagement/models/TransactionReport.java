package com.codechallenge.accountmanagement.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class TransactionReport implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    String date;
    String customer;
    String account;
    String accountType;
    BigDecimal initialBalance;
    boolean status;
    String movementType;
    BigDecimal amount;
    BigDecimal balance;

}

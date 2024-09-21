package com.codechallenge.accountmanagement.entities;

import com.codechallenge.accountmanagement.util.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
public class BankAccount implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number", unique = true)
    @NotNull(message = "accountNumber cannot be null")
    private String accountNumber;

    @Column(name = "account_type")
    @NotNull(message = "accountType cannot be null")
    private AccountType accountType;

    @Column(name = "initial_balance")
    @NotNull(message = "initialBalance cannot be null")
    private BigDecimal initialBalance;

    @Column(name = "total_balance")
    private BigDecimal totalBalance;

    boolean status;

    @Column(name = "customer_id")
    @NotNull(message = "customerId cannot be null")
    private Integer customerId;

}

    package com.codechallenge.accountmanagement.entities;

    import com.codechallenge.accountmanagement.util.enums.MovementType;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.io.Serial;
    import java.io.Serializable;
    import java.math.BigDecimal;
    import java.util.Date;

    @Entity
    @Table(name = "banking_movement")
    @Getter
    @Setter
    @NoArgsConstructor
    public class BankingMovement implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private Date date;

        @Column(name = "movement_type")
        @NotNull(message = "movementType cannot be null")
        private MovementType movementType;

        @NotNull(message = "amount cannot be null")
        private BigDecimal amount;

        private BigDecimal balance;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
        @NotNull(message = "bankAccountId cannot be null")
        private BankAccount bankAccount;


        public BankingMovement(Date date, MovementType movementType, BigDecimal amount, BigDecimal balance, BankAccount bankAccount) {
            this.date = date;
            this.movementType = movementType;
            this.amount = amount;
            this.balance = balance;
            this.bankAccount = bankAccount;
        }
    }

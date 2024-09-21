package com.codechallenge.accountmanagement.services.impl;

import com.codechallenge.accountmanagement.entities.BankAccount;
import com.codechallenge.accountmanagement.entities.BankingMovement;
import com.codechallenge.accountmanagement.repositories.BankAccountRepository;
import com.codechallenge.accountmanagement.repositories.BankingMovementRepository;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.codechallenge.accountmanagement.utils.MockUtils.mockBankAccount;
import static com.codechallenge.accountmanagement.utils.MockUtils.mockBankingMovement;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankingMovementServiceImplTest {

    @Mock
    private BankingMovementRepository bankingMovementRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    BankingMovementServiceImpl bankingMovementServiceImpl;

    @Test
    void testCreateBankingMovement() throws InterruptedException {
        BankingMovement bankingMovement = mockBankingMovement();
        BankAccount bankAccount = mockBankAccount();

        when(bankingMovementRepository.save(any(BankingMovement.class))).thenReturn(bankingMovement);
        when(bankAccountRepository.findById(any(Integer.class))).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        TestObserver<BankingMovement> testObserver = bankingMovementServiceImpl.create(bankingMovement).test();
        testObserver.await();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(bankingMovement);
    }

}
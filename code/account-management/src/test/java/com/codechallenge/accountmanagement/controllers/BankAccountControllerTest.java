package com.codechallenge.accountmanagement.controllers;

import com.codechallenge.accountmanagement.entities.BankAccount;
import com.codechallenge.accountmanagement.services.BankAccountService;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.codechallenge.accountmanagement.utils.MockUtils.mockBankAccount;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;

    @Test
    void testCreateAccount() throws InterruptedException {

        BankAccount bankAccount = mockBankAccount();

        when(bankAccountService.create(any(BankAccount.class))).thenReturn(Single.just(bankAccount));

        TestObserver<ResponseEntity<BankAccount>> testObserver = bankAccountController.create(bankAccount).test();
        testObserver.await();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(ResponseEntity.status(HttpStatus.CREATED).body(bankAccount));

    }

}
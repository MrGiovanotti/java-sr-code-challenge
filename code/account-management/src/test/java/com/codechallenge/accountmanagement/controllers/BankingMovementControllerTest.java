package com.codechallenge.accountmanagement.controllers;

import com.codechallenge.accountmanagement.entities.BankingMovement;
import com.codechallenge.accountmanagement.services.BankingMovementService;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.codechallenge.accountmanagement.utils.MockUtils.mockBankingMovement;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankingMovementControllerTest {

    @Mock
    private BankingMovementService bankingMovementService;

    @InjectMocks
    private BankingMovementController bankingMovementController;

    @Test
    void testCreateBankingMovement() throws InterruptedException {
        BankingMovement bankingMovement = mockBankingMovement();

        when(bankingMovementService.create(any(BankingMovement.class))).thenReturn(Single.just(bankingMovement));

        TestObserver<ResponseEntity<BankingMovement>> testObserver = bankingMovementController.create(bankingMovement).test();
        testObserver.await();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(ResponseEntity.status(HttpStatus.CREATED).body(bankingMovement));
    }

}
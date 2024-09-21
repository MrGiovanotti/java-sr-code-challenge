package com.codechallenge.accountmanagement.services.impl;

import com.codechallenge.accountmanagement.entities.BankAccount;
import com.codechallenge.accountmanagement.entities.BankingMovement;
import com.codechallenge.accountmanagement.exceptions.CustomerNotFoundException;
import com.codechallenge.accountmanagement.models.Customer;
import com.codechallenge.accountmanagement.repositories.BankAccountRepository;
import com.codechallenge.accountmanagement.repositories.BankingMovementRepository;
import com.codechallenge.accountmanagement.services.CustomerService;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.codechallenge.accountmanagement.utils.MockUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private  BankingMovementRepository bankingMovementRepository;

    @InjectMocks
    BankAccountServiceImpl bankAccountServiceImpl;

    @Test
    void testCreateBankAccount() throws InterruptedException {

        BankAccount bankAccount = mockBankAccount();
        Customer customer = mockCustomer();
        BankingMovement bankingMovement = mockBankingMovement();


        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        when(customerService.getCustomerById(any(Integer.class))).thenReturn(Maybe.just(customer));
        when(bankingMovementRepository.save(any(BankingMovement.class))).thenReturn(bankingMovement);

        TestObserver<BankAccount> testObserver = bankAccountServiceImpl.create(bankAccount).test();
        testObserver.await();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(bankAccount);

    }

    @Test
    void createBankAccountWhenCustomerNotFound() throws InterruptedException {
        BankAccount bankAccount = mockBankAccount();
        when(customerService.getCustomerById(any(Integer.class))).thenReturn(Maybe.empty());
        TestObserver<BankAccount> testObserver = bankAccountServiceImpl.create(bankAccount).test();
        testObserver.await();
        testObserver.assertNotComplete();
        testObserver.assertError(throwable ->
                throwable instanceof CustomerNotFoundException &&
                        throwable.getMessage().equals("El cliente no existe.")
        );
    }

}
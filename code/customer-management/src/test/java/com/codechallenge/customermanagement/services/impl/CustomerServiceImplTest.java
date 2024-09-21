package com.codechallenge.customermanagement.services.impl;

import com.codechallenge.customermanagement.entities.Customer;
import com.codechallenge.customermanagement.repositories.CustomerRepository;
import com.codechallenge.customermanagement.util.MockUtils;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    @Test
    void findByIdWhenCustomerExists() throws InterruptedException {
        Customer customer = MockUtils.mockCustomer();

        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));

        TestObserver<Customer> testObserver = customerServiceImpl.findById(1).test();
        testObserver.await();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(customer);
    }

}
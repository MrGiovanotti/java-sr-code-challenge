package com.codechallenge.customermanagement.controllers;

import com.codechallenge.customermanagement.entities.Customer;
import com.codechallenge.customermanagement.services.CustomerService;
import com.codechallenge.customermanagement.util.MockUtils;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerOrderController;

    @Test
    void viewCustomerWhenCustomerExists() throws InterruptedException {
        Customer customer = MockUtils.mockCustomer();
        when(customerService.findById(anyInt())).thenReturn(Maybe.just(customer));

        TestObserver<ResponseEntity<Customer>> testObserver = customerOrderController.view(1).test();
        testObserver.await();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(ResponseEntity.ok(customer));
    }

}
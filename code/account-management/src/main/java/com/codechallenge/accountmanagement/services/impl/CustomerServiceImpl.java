package com.codechallenge.accountmanagement.services.impl;

import com.codechallenge.accountmanagement.clients.CustomerServiceClient;
import com.codechallenge.accountmanagement.models.Customer;
import com.codechallenge.accountmanagement.services.CustomerService;
import io.reactivex.rxjava3.core.Maybe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerServiceClient customerServiceClient;

    @Override
    public Maybe<Customer> getCustomerById(Integer id) {
        return customerServiceClient.getCustomerById(id)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        return Maybe.just(response.body());
                    } else {
                        return Maybe.empty();
                    }
                });
    }
}

package com.codechallenge.accountmanagement.services;

import com.codechallenge.accountmanagement.models.Customer;
import io.reactivex.rxjava3.core.Maybe;

public interface CustomerService {

    Maybe<Customer> getCustomerById(Integer id);

}

package com.codechallenge.customermanagement.services;

import com.codechallenge.customermanagement.entities.Customer;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface CustomerService {

    Maybe<Customer> findById(Integer id);

    Flowable<Customer> findAll();

    Single<Customer> save(Customer customer);

    Single<Customer> update(Customer customer);

    Completable delete(Integer id);

}

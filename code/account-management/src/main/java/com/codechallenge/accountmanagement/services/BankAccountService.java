package com.codechallenge.accountmanagement.services;

import com.codechallenge.accountmanagement.entities.BankAccount;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface BankAccountService {

    Maybe<BankAccount> findById(Integer id);

    Flowable<BankAccount> findAll();

    Single<BankAccount> create(BankAccount bankAccount);

    Single<BankAccount> update(BankAccount bankAccount);

    Completable delete(Integer id);

}

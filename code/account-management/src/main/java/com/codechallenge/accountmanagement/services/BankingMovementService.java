package com.codechallenge.accountmanagement.services;

import com.codechallenge.accountmanagement.entities.BankingMovement;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface BankingMovementService {

    Maybe<BankingMovement> findById(Integer id);

    Flowable<BankingMovement> findAll();

    Single<BankingMovement> create(BankingMovement bankingMovement);

    Single<BankingMovement> update(BankingMovement bankingMovement);

    Completable delete(Integer id);

}

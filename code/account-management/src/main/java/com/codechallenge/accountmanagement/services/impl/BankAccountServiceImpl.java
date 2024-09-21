package com.codechallenge.accountmanagement.services.impl;

import com.codechallenge.accountmanagement.entities.BankAccount;
import com.codechallenge.accountmanagement.entities.BankingMovement;
import com.codechallenge.accountmanagement.exceptions.BankAccountNotFoundException;
import com.codechallenge.accountmanagement.exceptions.CustomerNotFoundException;
import com.codechallenge.accountmanagement.exceptions.DuplicateAccountNumberException;
import com.codechallenge.accountmanagement.exceptions.GenericException;
import com.codechallenge.accountmanagement.repositories.BankAccountRepository;
import com.codechallenge.accountmanagement.repositories.BankingMovementRepository;
import com.codechallenge.accountmanagement.services.BankAccountService;
import com.codechallenge.accountmanagement.services.CustomerService;
import com.codechallenge.accountmanagement.util.enums.MovementType;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerService customerService;
    private final BankingMovementRepository bankingMovementRepository;

    @Override
    public Maybe<BankAccount> findById(Integer id) {
        return Maybe.defer(() -> bankAccountRepository.findById(id)
                        .map(Maybe::just).orElse(Maybe.empty()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<BankAccount> findAll() {
        return Flowable.defer(() -> Flowable.fromIterable(bankAccountRepository.findAll()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<BankAccount> create(BankAccount bankAccount) {

        return customerService.getCustomerById(bankAccount.getCustomerId())
                .switchIfEmpty(Single.error(new CustomerNotFoundException("El cliente no existe.")))
                .flatMap(customer -> Single.fromCallable(() -> {
                    bankAccount.setTotalBalance(bankAccount.getInitialBalance());
                    return bankAccountRepository.save(bankAccount);
                }))
                .flatMap(account -> Single.fromCallable(() -> bankingMovementRepository.save(
                                new BankingMovement(new Date(), MovementType.DEPOSIT, account.getInitialBalance(), account.getInitialBalance(), account)))
                        .ignoreElement()
                        .andThen(Single.just(account)))
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {
                    log.error("BankAccountServiceImpl -> Error al guardar la cuenta", throwable);

                    if (throwable instanceof DataIntegrityViolationException && throwable.getMessage().contains("account_number")) {
                        return Single.error(new DuplicateAccountNumberException("La cuenta ya existe."));
                    }

                    if (throwable instanceof CustomerNotFoundException) {
                        return Single.error(throwable);
                    }

                    return Single.error(new GenericException("No se pudo guardar la cuenta", throwable));
                });
    }

    @Override
    public Single<BankAccount> update(BankAccount bankAccount) {
        return findById(bankAccount.getId())
                .switchIfEmpty(Single.error(new BankAccountNotFoundException("La cuenta que desea actualizar no existe.")))
                .flatMap(existingAccount -> Single.fromCallable(() -> {
                    existingAccount.setAccountNumber(bankAccount.getAccountNumber());
                    existingAccount.setAccountType(bankAccount.getAccountType());
                    existingAccount.setTotalBalance(bankAccount.getTotalBalance());
                    existingAccount.setInitialBalance(bankAccount.getInitialBalance());
                    existingAccount.setStatus(bankAccount.isStatus());
                    return bankAccountRepository.save(existingAccount);
                }))
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {

                    if (throwable instanceof BankAccountNotFoundException) {
                        return Single.error(throwable);
                    }

                    log.error("BankAccountServiceImpl -> Error al actualizar la cuenta", throwable);
                    return Single.error(new GenericException("No se pudo actualizar la cuenta", throwable));
                });
    }

    @Override
    public Completable delete(Integer id) {
        return findById(id)
                .switchIfEmpty(Single.error(new BankAccountNotFoundException("La cuenta que desea eliminar no existe")))
                .flatMapCompletable(existingAccount -> Completable.fromAction(() -> bankAccountRepository.delete(existingAccount)))
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {

                    if (throwable instanceof BankAccountNotFoundException) {
                        return Completable.error(throwable);
                    }

                    log.error("BankAccountServiceImpl -> Error al eliminar la cuenta", throwable);
                    return Completable.error(new GenericException("No se pudo eliminar la cuenta.", throwable));
                });
    }
}

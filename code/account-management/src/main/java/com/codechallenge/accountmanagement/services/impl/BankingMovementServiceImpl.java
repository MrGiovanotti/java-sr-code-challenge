package com.codechallenge.accountmanagement.services.impl;

import com.codechallenge.accountmanagement.entities.BankAccount;
import com.codechallenge.accountmanagement.entities.BankingMovement;
import com.codechallenge.accountmanagement.exceptions.*;
import com.codechallenge.accountmanagement.repositories.BankAccountRepository;
import com.codechallenge.accountmanagement.repositories.BankingMovementRepository;
import com.codechallenge.accountmanagement.services.BankingMovementService;
import com.codechallenge.accountmanagement.util.enums.MovementType;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankingMovementServiceImpl implements BankingMovementService {

    private final BankingMovementRepository bankingMovementRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public Maybe<BankingMovement> findById(Integer id) {
        return Maybe.defer(() -> bankingMovementRepository.findById(id)
                        .map(Maybe::just).orElse(Maybe.empty()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<BankingMovement> findAll() {
        return Flowable.defer(() -> Flowable.fromIterable(bankingMovementRepository.findAll()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<BankingMovement> create(BankingMovement bankingMovement) {

        BigDecimal amount = bankingMovement.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return Single.error(new IncorrectAmountException("El monto del movimiento bancario no puede ser cero."));
        }

        if ((bankingMovement.getMovementType() == MovementType.DEPOSIT && amount.compareTo(BigDecimal.ZERO) < 0) ||
                (bankingMovement.getMovementType() == MovementType.WITHDRAW && amount.compareTo(BigDecimal.ZERO) > 0)) {
            return Single.error(new IncorrectAmountException("Monto incorrecto según el tipo de movimiento."));
        }

        return Single.fromCallable(() -> bankAccountRepository.findById(bankingMovement.getBankAccount().getId()))
                .flatMap(optionalBankAccount -> {
                    if (optionalBankAccount.isEmpty()) {
                        return Single.error(new BankAccountNotFoundException("La cuenta bancaria no existe."));
                    }

                    BankAccount bankAccount = optionalBankAccount.get();
                    BigDecimal newBalance = bankAccount.getTotalBalance().add(amount);

                    if (bankingMovement.getMovementType() == MovementType.WITHDRAW && newBalance.compareTo(BigDecimal.ZERO) < 0) {
                        return Single.error(new InsufficientBalanceException("Saldo no disponible"));
                    }

                    bankingMovement.setDate(new Date());
                    bankingMovement.setBalance(newBalance);
                    bankAccount.setTotalBalance(newBalance);

                    return Single.fromCallable(() -> bankingMovementRepository.save(bankingMovement))
                            .flatMap(savedMovement -> {
                                bankAccountRepository.save(bankAccount);
                                savedMovement.setBankAccount(bankAccount);
                                return Single.just(savedMovement);
                            });
                })
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(this::handleSaveMovementError);
    }

    private Single<BankingMovement> handleSaveMovementError(Throwable throwable) {
        if (throwable instanceof BankAccountNotFoundException || throwable instanceof IncorrectAmountException ||
                throwable instanceof InsufficientBalanceException) {
            return Single.error(throwable);
        }

        log.error("BankingMovementServiceImpl -> Error al registrar el movimiento bancario.", throwable);
        return Single.error(new GenericException("No se pudo registrar el movimiento bancario.", throwable));
    }


    @Override
    public Single<BankingMovement> update(BankingMovement bankingMovement) {
        return findById(bankingMovement.getId())
                .switchIfEmpty(Single.error(new BankingMovementNotFoundException("El movimiento bancario no existe.")))
                .flatMap(existingMovement -> Single.fromCallable(() -> {
                    existingMovement.setDate(bankingMovement.getDate());
                    existingMovement.setMovementType(bankingMovement.getMovementType());
                    existingMovement.setAmount(bankingMovement.getAmount());
                    existingMovement.setBalance(bankingMovement.getBalance());
                    return bankingMovementRepository.save(existingMovement);
                }))
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {

                    if (throwable instanceof BankingMovementNotFoundException) {
                        return Single.error(throwable);
                    }

                    log.error("BankingMovementServiceImpl -> Error al actualizar el movimiento bancario.", throwable);
                    return Single.error(new GenericException("No se pudo actualizar el movimiento bancario.", throwable));
                });
    }

    @Override
    public Completable delete(Integer id) {
        return findById(id)
                .switchIfEmpty(Single.error(new BankingMovementNotFoundException("El movimiento que desea eliminar no existe.")))
                .flatMapCompletable(existingMovement -> Completable.fromAction(() -> bankingMovementRepository.delete(existingMovement)))
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {

                    if (throwable instanceof BankingMovementNotFoundException) {
                        return Completable.error(throwable);
                    }

                    log.error("BankingMovementServiceImpl -> Error al eliminar el movimiento bancario.", throwable);
                    return Completable.error(new GenericException("No se pudo eliminar el movimiento bancario.", throwable));
                });
    }
}

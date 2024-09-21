package com.codechallenge.accountmanagement.controllers;

import com.codechallenge.accountmanagement.entities.BankAccount;
import com.codechallenge.accountmanagement.services.BankAccountService;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cuentas")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping("/{id}")
    public Maybe<ResponseEntity<BankAccount>> view(@PathVariable Integer id) {
        return bankAccountService.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Maybe.just(ResponseEntity.notFound().build()));

    }

    @GetMapping
    public Single<ResponseEntity<List<BankAccount>>> list() {
        return bankAccountService
                .findAll()
                .toList()
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Single<ResponseEntity<BankAccount>> create(@RequestBody BankAccount bankAccount) {
        return bankAccountService.create(bankAccount)
                .map(bankAccountCreated -> ResponseEntity.status(HttpStatus.CREATED).body(bankAccountCreated));
    }

    @PutMapping
    public Single<ResponseEntity<BankAccount>> update(@RequestBody BankAccount bankAccount) {
        return bankAccountService.update(bankAccount)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Single<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return bankAccountService.delete(id)
                .andThen(Single.just(ResponseEntity.noContent().build()));
    }

}

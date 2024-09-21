package com.codechallenge.accountmanagement.controllers;

import com.codechallenge.accountmanagement.entities.BankingMovement;
import com.codechallenge.accountmanagement.services.BankingMovementService;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movimientos")
public class BankingMovementController {

    private final BankingMovementService bankingMovementService;

    @GetMapping("/{id}")
    public Maybe<ResponseEntity<BankingMovement>> view(@PathVariable Integer id) {
        return bankingMovementService.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Maybe.just(ResponseEntity.notFound().build()));

    }

    @GetMapping
    public Single<ResponseEntity<List<BankingMovement>>> list() {
        return bankingMovementService
                .findAll()
                .toList()
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Single<ResponseEntity<BankingMovement>> create(@RequestBody BankingMovement bankingMovement) {
        return bankingMovementService.create(bankingMovement)
                .map(movementCreated -> ResponseEntity.status(HttpStatus.CREATED).body(movementCreated));
    }

    @PutMapping
    public Single<ResponseEntity<BankingMovement>> update(@RequestBody BankingMovement bankingMovement) {
        return bankingMovementService.update(bankingMovement)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Single<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return bankingMovementService.delete(id)
                .andThen(Single.just(ResponseEntity.noContent().build()));
    }

}

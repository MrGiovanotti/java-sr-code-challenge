package com.codechallenge.customermanagement.controllers;

import com.codechallenge.customermanagement.entities.Customer;
import com.codechallenge.customermanagement.services.CustomerService;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Maybe<ResponseEntity<Customer>> view(@PathVariable Integer id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Maybe.just(ResponseEntity.notFound().build()));

    }

    @GetMapping
    public Single<ResponseEntity<List<Customer>>> list() {
        return customerService
                .findAll()
                .toList()
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Single<ResponseEntity<Customer>> create(@RequestBody Customer customer) {
        return customerService.save(customer)
                .map(savedCustomer -> ResponseEntity.status(201).body(savedCustomer));
    }

    @PutMapping
    public Single<ResponseEntity<Customer>> update(@RequestBody Customer customer) {
        return customerService.update(customer)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Single<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return customerService.delete(id)
                .andThen(Single.just(ResponseEntity.noContent().build()));
    }

}

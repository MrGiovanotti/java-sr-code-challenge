package com.codechallenge.customermanagement.services.impl;

import com.codechallenge.customermanagement.entities.Customer;
import com.codechallenge.customermanagement.exceptions.CustomerDuplicateIdentificationException;
import com.codechallenge.customermanagement.exceptions.CustomerNotFoundException;
import com.codechallenge.customermanagement.exceptions.GenericException;
import com.codechallenge.customermanagement.repositories.CustomerRepository;
import com.codechallenge.customermanagement.services.CustomerService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Maybe<Customer> findById(Integer id) {
        return Maybe.defer(() -> customerRepository.findById(id)
                .map(Maybe::just).orElse(Maybe.empty()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<Customer> findAll() {
        return Flowable.defer(() -> Flowable.fromIterable(customerRepository.findAll()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Customer> save(Customer customer) {
        return Single.fromCallable(() -> customerRepository.save(customer))
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {
                    log.error("CustomerServiceImpl -> Error al guardar el cliente.", throwable);

                    if (throwable instanceof DataIntegrityViolationException && throwable.getMessage().contains("identification_number.")) {
                        return Single.error(new CustomerDuplicateIdentificationException("Ya existe un cliente con esta identificación."));
                    }

                    return Single.error(new GenericException("No se pudo guardar el cliente.", throwable));
                });
    }

    @Override
    public Single<Customer> update(Customer customer) {
        return findById(customer.getId())
                .switchIfEmpty(Single.error(new CustomerNotFoundException("El cliente que desea actualizar no existe.")))
                .flatMap(existingCustomer -> Single.fromCallable(() -> {
                    existingCustomer.setName(customer.getName());
                    existingCustomer.setGender(customer.getGender());
                    existingCustomer.setAge(customer.getAge());
                    existingCustomer.setIdentificationNumber(customer.getIdentificationNumber());
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setPhone(customer.getPhone());
                    existingCustomer.setPassword(customer.getPassword());
                    existingCustomer.setStatus(customer.isStatus());
                    return customerRepository.save(existingCustomer);
                }))
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {

                    if (throwable instanceof CustomerNotFoundException) {
                        return Single.error(throwable);
                    }

                    log.error("CustomerServiceImpl -> Error al actualizar el cliente.", throwable);
                    return Single.error(new GenericException("No se pudo actualizar el cliente.", throwable));
                });
    }

    @Override
    public Completable delete(Integer id) {
        return findById(id)
                .switchIfEmpty(Single.error(new CustomerNotFoundException("El cliente que desea eliminar no existe.")))
                .flatMapCompletable(existingCustomer -> Completable.fromAction(() -> customerRepository.delete(existingCustomer)))
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {

                    if (throwable instanceof CustomerNotFoundException) {
                        return Completable.error(throwable);
                    }

                    log.error("CustomerServiceImpl -> Error al eliminar el cliente", throwable);
                    return Completable.error(new GenericException("No se pudo eliminar el cliente.", throwable));
                });
    }
}

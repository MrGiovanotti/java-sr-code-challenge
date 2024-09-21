package com.codechallenge.accountmanagement.services.impl;

import com.codechallenge.accountmanagement.exceptions.GenericException;
import com.codechallenge.accountmanagement.models.TransactionReport;
import com.codechallenge.accountmanagement.repositories.BankingMovementRepository;
import com.codechallenge.accountmanagement.services.CustomerService;
import com.codechallenge.accountmanagement.services.ReportService;
import com.codechallenge.accountmanagement.util.Util;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final BankingMovementRepository bankingMovementRepository;
    private final CustomerService customerService;

    @Override
    public Flowable<TransactionReport> getTransactionReport(String dateRange) {
        Date[] dates = Util.convertToDates(dateRange);

        return Flowable.fromIterable(bankingMovementRepository
                .findAllMovementsFromDateToDate(dates[0], dates[1]))
                .flatMap(bankingMovement -> {
                    Integer customerId = bankingMovement.getBankAccount().getCustomerId();
                    return customerService.getCustomerById(customerId)
                            .toFlowable()
                            .map(customer -> {
                                TransactionReport transactionReport = new TransactionReport();
                                transactionReport.setDate(bankingMovement.getDate().toString());
                                transactionReport.setCustomer(customer.getName());
                                transactionReport.setAccount(bankingMovement.getBankAccount().getAccountNumber());
                                transactionReport.setAccountType(bankingMovement.getBankAccount().getAccountType().toString());
                                transactionReport.setInitialBalance(bankingMovement.getBankAccount().getInitialBalance());
                                transactionReport.setStatus(bankingMovement.getBankAccount().isStatus());
                                transactionReport.setMovementType(bankingMovement.getMovementType().toString());
                                transactionReport.setAmount(bankingMovement.getAmount());
                                transactionReport.setBalance(bankingMovement.getBankAccount().getTotalBalance());
                                return transactionReport;
                            })
                            .subscribeOn(Schedulers.io())
                            .onErrorResumeNext(throwable -> {
                                log.error("ReportServiceImpl -> Error al generar el reporte de transacciones.", throwable);
                                return Flowable.error(new GenericException("No se pudo generar el reporte de transacciones.", throwable));
                            });
                });

    }
}

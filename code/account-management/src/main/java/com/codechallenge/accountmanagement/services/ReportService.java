package com.codechallenge.accountmanagement.services;

import com.codechallenge.accountmanagement.models.TransactionReport;
import io.reactivex.rxjava3.core.Flowable;

public interface ReportService {

    Flowable<TransactionReport> getTransactionReport(String dateRange);

}

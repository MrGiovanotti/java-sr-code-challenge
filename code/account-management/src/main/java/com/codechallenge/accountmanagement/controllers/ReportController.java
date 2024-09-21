package com.codechallenge.accountmanagement.controllers;

import com.codechallenge.accountmanagement.models.TransactionReport;
import com.codechallenge.accountmanagement.services.ReportService;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reportes")
public class ReportController {

    private final ReportService reportService;



    @GetMapping
    public Single<ResponseEntity<List<TransactionReport>>> list(@RequestParam("fecha") String dateRange) {
        return reportService
                .getTransactionReport(dateRange)
                .toList()
                .map(ResponseEntity::ok);
    }

}

package com.codechallenge.accountmanagement.repositories;

import com.codechallenge.accountmanagement.entities.BankingMovement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BankingMovementRepository extends CrudRepository<BankingMovement, Integer> {

    @Query("SELECT b FROM BankingMovement b WHERE b.date >= ?1 AND b.date <= ?2")
    List<BankingMovement> findAllMovementsFromDateToDate(Date fromDate, Date toDate);

}

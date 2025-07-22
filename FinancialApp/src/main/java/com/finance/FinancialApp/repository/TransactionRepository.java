package com.finance.FinancialApp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.FinancialApp.Transaction;

public interface  TransactionRepository extends JpaRepository<Transaction, Integer>{
    List<Transaction> findByUserId(final Integer userId);

    // to sort transactions by date
    List<Transaction> findUserByIdAndDateBetween(final Integer userId, final LocalDate startDate, final LocalDate endDate);
}
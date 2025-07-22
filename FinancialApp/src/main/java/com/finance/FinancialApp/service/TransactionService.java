package com.finance.FinancialApp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finance.FinancialApp.Transaction;
import com.finance.FinancialApp.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(final TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public List<Transaction> findByUserId(final Integer userId) {
        return repository.findByUserId(userId);
    }

    // to find transactions by date
    public List<Transaction> findByUserIdAndDateBetween(final Integer userId, final LocalDate startDate, LocalDate endDate) {
        return repository.findUserByIdAndDateBetween(userId, startDate, endDate);
    }

    // saving transaction
    public Transaction save(final Transaction transaction) {
        return repository.save(transaction);
    }

    public Optional<Transaction> findById(final Integer id) {
        return repository.findById(id);
    }

    public boolean deleteById(final Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
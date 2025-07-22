package com.finance.FinancialApp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.FinancialApp.Transaction;
import com.finance.FinancialApp.TransactionDTO;
import com.finance.FinancialApp.User;
import com.finance.FinancialApp.service.TransactionService;
import com.finance.FinancialApp.service.UserService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(final TransactionService transactionService, final UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addTranscation(final @RequestBody TransactionDTO dto) {
        // getting login from context
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        //  serching user by login
        User user = userService.findByLogin(login)
                               .orElseThrow(() -> new RuntimeException("User is not found!"));
        
        // creating new transaction
        Transaction transaction = new Transaction();
        transaction.setName(dto.getName());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate());
        transaction.setSum(dto.getSum());
        transaction.setUser(user);

        final Transaction saved = transactionService.save(transaction);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(saved);
    }

    @GetMapping
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<?> getTransactionByUserId(final @PathVariable Integer userId) {
        List<Transaction> transactions = transactionService.findByUserId(userId);
        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions with such userId!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    // gets transcations by date
    @GetMapping("user/{userId}")
    public List<Transaction> getUserTransactionsByDate(
        final @PathVariable Integer userId,
        final @RequestParam String start,
        final @RequestParam String end) {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            return transactionService.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    @GetMapping("/{id}")
    public Optional<Transaction> getTransactionsById(final @PathVariable Integer id) {
        return transactionService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(final @PathVariable Integer id) {
        if (transactionService.deleteById(id)) {
            return ResponseEntity.ok("Transcation is deleted!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Transaction is not found!");
    }
}

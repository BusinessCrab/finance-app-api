package com.finance.FinancialApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.FinancialApp.User;

public interface  UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(final String login);
}
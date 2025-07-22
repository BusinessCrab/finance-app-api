package com.finance.FinancialApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance.FinancialApp.User;
import com.finance.FinancialApp.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository repository, final PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findByLogin(final String login) {
        return repository.findByLogin(login);
    }

    // saving user
    public User save(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Optional<User> findById(final Integer id) {
        return repository.findById(id);
    }

    public boolean deleteById(final Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // does this login already exist?
    public boolean loginExists(final String login) {
        return repository.findByLogin(login).isPresent();
    }

    // checks if the password matches with encoded password
    public boolean checkPassword(final String rawPassword, final String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

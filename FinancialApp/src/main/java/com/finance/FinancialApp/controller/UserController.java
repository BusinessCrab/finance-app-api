package com.finance.FinancialApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.FinancialApp.User;
import com.finance.FinancialApp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    // New User Registration
    public ResponseEntity<?> resgisterUser(final @Valid @RequestBody User user) {
        System.out.println("Controller is running!");
        if (userService.loginExists(user.getLogin())) {
            System.out.println("User already exsists!");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Login is already in use!");
        }
        User saved = userService.save(user);
        System.out.println("User has been saved!");
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("by-login/{login}")
    public Optional<User> getUserByLogin(final @PathVariable String login) {
        return userService.findByLogin(login);
    }

    @GetMapping("/{id}")
    // Getting User by Id
    public Optional<User> getUserById(final @PathVariable Integer id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    // Delete User by Id
    public ResponseEntity<?> deleteUser(final @PathVariable Integer id) {
        if (userService.deleteById(id)) {
            return ResponseEntity.ok("User deleted!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found!");
    }
}

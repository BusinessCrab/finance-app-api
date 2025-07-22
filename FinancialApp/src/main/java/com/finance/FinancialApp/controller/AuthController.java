package com.finance.FinancialApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.FinancialApp.User;
import com.finance.FinancialApp.auth.AuthRequest;
import com.finance.FinancialApp.auth.AuthResponse;
import com.finance.FinancialApp.repository.UserRepository;
import com.finance.FinancialApp.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(final @RequestBody AuthRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()) 
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        String token = jwtUtil.generateToken(userDetails.toString());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> resgister(final @RequestBody AuthRequest request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists!");
        }

        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}

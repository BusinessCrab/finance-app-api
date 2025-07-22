package com.finance.FinancialApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(final JwtAuthenticationFilter jwtAuthenticationFilter,
                          final CustomUserDetailedService customUserDetailedService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // 
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                   .authorizeHttpRequests(auth -> auth.requestMatchers("/api/users", "/api/auth/**")
                                                      .permitAll()
                                                      .anyRequest()
                                                      .authenticated()
                   )
                   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                   .build();
    }

    // hashing of password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

package com.finance.FinancialApp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finance.FinancialApp.User;
import com.finance.FinancialApp.repository.UserRepository;

// @Service registers class as Spring Bean
@Service
// UserDetailsService is used to upload users
public class CustomUserDetailedService implements UserDetailsService{
    
    private final UserRepository userRepository;

    public CustomUserDetailedService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                                  .orElseThrow(() -> new UsernameNotFoundException("User is not found!"));

        return org.springframework.security.core.userdetails.User.withUsername(user.getLogin()) // login
                                                                 .password(user.getPassword()) // password
                                                                 .authorities("USER") //role
                                                                 .build();
    }
}

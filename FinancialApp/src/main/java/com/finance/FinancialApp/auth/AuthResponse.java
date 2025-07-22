package com.finance.FinancialApp.auth;

public class AuthResponse {
    private final String token;

    public AuthResponse(final String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}

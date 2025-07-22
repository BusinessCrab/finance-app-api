package com.finance.FinancialApp.auth;

public class AuthRequest {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }
    public void getUsername(final String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(final String password) {
        this.password = password;
    }
}

package com.finance.FinancialApp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="Login is needed!")
    @Size(min=3, max=50, message="Login length must be between 3 and 50 symbols!")
    @Column(nullable = false, unique=true)
    private String login;

    @NotBlank(message="Password is needed!")
    @Size(min=8, message="Password length must contain more or equals 8 symbols!")
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private List<Transaction> transactions;

    public User() {}
    
    public int getId() {
        return id;
    }
    public void setId(final Integer id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(final String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(final String password) {
        this.password = password;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(final List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    @Override
    public String toString() {
        return 
        "Id: " + id +
        "\nLogin: " + login +
        "\nPassword: " + password + // need to be removed in future
        "\nTransactions: " + transactions;
    }
}
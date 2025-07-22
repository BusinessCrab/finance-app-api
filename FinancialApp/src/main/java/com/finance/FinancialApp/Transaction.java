package com.finance.FinancialApp;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="Name is needed!")
    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;
    
    @NotNull(message="Sum is needed!")
    @Column(nullable=false)
    private Long sum;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable=false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference
    private User user;

    public Transaction() {}

    public Integer getId() {
        return id;
    }
    public void setId(final Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    public Long getSum() {
        return sum;
    }
    public void setSum(final Long sum) {
        this.sum = sum;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(final LocalDate date) {
        this.date = date;
    }
    public User getUser() {
        return user;
    }
    public void setUser(final User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return 
        "Name: " + name +
        "\nDescription: " + description +
        "\nSum: " + sum +
        "\nDate: " + date; 
    }
}
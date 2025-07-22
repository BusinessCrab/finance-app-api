package com.finance.FinancialApp;

import java.time.LocalDate;

public class TransactionDTO {
    private String name;
    private String description;
    private Long sum;
    private LocalDate date;
    private Integer userId;

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
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(final Integer userId) {
        this.userId = userId;
    }
}

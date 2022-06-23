package com.MindHub.homebanking.dtos;

import com.MindHub.homebanking.models.Transaction;
import com.MindHub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private LocalDateTime date;
    private double amount, currentBalance;
    private String description;
    private TransactionType transactionType;

    public TransactionDTO(){}

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.transactionType = transaction.getTransactionType();
        this.currentBalance = transaction.getCurrentBalance();
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
}

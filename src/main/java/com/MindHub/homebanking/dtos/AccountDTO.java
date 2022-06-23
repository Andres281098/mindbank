package com.MindHub.homebanking.dtos;

import com.MindHub.homebanking.models.Account;
import com.MindHub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class AccountDTO {

    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private  boolean disable;
    private AccountType accountType;
    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(){}

    public AccountDTO(Account account) {
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.id = account.getId();
        this.transactions = account.getTransaction().stream().map(TransactionDTO::new).collect(toSet());
        this.disable = account.isDisable();
        this.accountType = account.getAccountType();
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isDisable(){return disable;}

    public AccountType getAccountType() {
        return accountType;
    }
}

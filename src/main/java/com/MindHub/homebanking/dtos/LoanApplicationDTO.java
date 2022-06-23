package com.MindHub.homebanking.dtos;

import com.MindHub.homebanking.models.Account;
import com.MindHub.homebanking.models.ClientLoan;
import com.MindHub.homebanking.models.Loan;

import java.util.List;

public class LoanApplicationDTO {
    private long loanId;
    private double amount;
    private int payments;
    private String destinationAccount;
    private float pctInterest;

    public LoanApplicationDTO(){}

    public LoanApplicationDTO(long loanId, double amount, int payments, String destinationAccount, float pctInterest){
    this.loanId= loanId;
    this.amount= amount;
    this.payments= payments;
    this.destinationAccount= destinationAccount;
    this.pctInterest = pctInterest;
    }

    public long getLoanId() {
        return loanId;
    }
    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }
    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }
    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public float getPctInterest() {
        return pctInterest;
    }
}

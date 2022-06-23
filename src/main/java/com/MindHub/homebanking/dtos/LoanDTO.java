package com.MindHub.homebanking.dtos;

import com.MindHub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private long id;
    private int maxAmount;
    private String name;
    private float pctInterest;
    private List<Integer> payments;

    public LoanDTO(){}

    public LoanDTO(Loan loan){
        this.id= loan.getId();
        this.maxAmount= loan.getMaxAmount();
        this.name= loan.getName();
        this.payments= loan.getPayments();
        this.pctInterest = loan.getPctInterest();
    }

    public long getId() {
        return id;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public float getPctInterest() {
        return pctInterest;
    }
}

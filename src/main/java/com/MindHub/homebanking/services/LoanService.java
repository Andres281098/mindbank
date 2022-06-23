package com.MindHub.homebanking.services;

import com.MindHub.homebanking.dtos.LoanDTO;
import com.MindHub.homebanking.models.ClientLoan;
import com.MindHub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getLoans();

    void saveLoan(Loan loan);

    Loan findById(Long id);

    void saveClientLoan(ClientLoan clientLoan);

}

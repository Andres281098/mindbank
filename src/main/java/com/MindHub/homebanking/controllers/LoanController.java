package com.MindHub.homebanking.controllers;

import com.MindHub.homebanking.dtos.LoanApplicationDTO;
import com.MindHub.homebanking.dtos.LoanDTO;
import com.MindHub.homebanking.models.*;
import com.MindHub.homebanking.services.AccountService;
import com.MindHub.homebanking.services.ClientService;
import com.MindHub.homebanking.services.LoanService;
import com.MindHub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.MindHub.homebanking.models.TransactionType.CREDIT;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    LoanService loanService;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping(path = "/loans")
    public ResponseEntity<Object> applyLoan (Authentication authentication,
                                             @RequestBody LoanApplicationDTO loanApplication){

    Loan loan = loanService.findById(loanApplication.getLoanId());
    Client client = clientService.findByMail(authentication.getName());
    Account destinationAccount = accountService.findByNumber(loanApplication.getDestinationAccount());
    double currentBalanceCredit = destinationAccount.getBalance() + loanApplication.getAmount();
    float loanInterest = 1 + loan.getPctInterest()/100;

        if (loanApplication.getAmount() == 0 || loanApplication.getPayments() == 0 || loanApplication.getLoanId() == 0){
            return new ResponseEntity<>("The fields can't be empty", HttpStatus.FORBIDDEN);
        }
        if (loan == null){
            return new ResponseEntity<>("This loan doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (loanApplication.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("The loan amount can't exceed the loan max. amount", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplication.getPayments())){
            return new ResponseEntity<>("Please select a valid option of payments", HttpStatus.FORBIDDEN);
        }
        if (loanApplication.getDestinationAccount() == null){
            return new ResponseEntity<>("The destination account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(destinationAccount)){
            return new ResponseEntity<>("The destination account must belong to you", HttpStatus.FORBIDDEN);
        }else {
            loanService.saveClientLoan(new ClientLoan(loanApplication.getAmount() * loanInterest, loanApplication.getPayments(), client, loan));
            transactionService.saveTransaction(new Transaction(LocalDateTime.now(), loanApplication.getAmount(), loan.getName() + " loan approved", CREDIT, destinationAccount, currentBalanceCredit));
            destinationAccount.setBalance(currentBalanceCredit);
            accountService.saveAccount(destinationAccount);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        }
    }

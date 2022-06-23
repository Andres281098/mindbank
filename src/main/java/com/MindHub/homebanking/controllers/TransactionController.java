package com.MindHub.homebanking.controllers;

import com.MindHub.homebanking.models.Account;
import com.MindHub.homebanking.models.Client;
import com.MindHub.homebanking.models.Transaction;
import com.MindHub.homebanking.services.AccountService;
import com.MindHub.homebanking.services.ClientService;
import com.MindHub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.MindHub.homebanking.models.TransactionType.CREDIT;
import static com.MindHub.homebanking.models.TransactionType.DEBIT;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction (Authentication authentication,
                                                     @RequestParam double amount,
                                                     @RequestParam String description,
                                                     @RequestParam String originAccount,
                                                     @RequestParam String destinationAccount){

        Client client = clientService.findByMail(authentication.getName());
        Account account = accountService.findByNumber(originAccount);
        Account account2 = accountService.findByNumber(destinationAccount);

        double currentBalanceCredit = account2.getBalance() + amount;
        double currentBalanceDebit = account.getBalance() - amount;

        if (amount == 0 || description.isEmpty() || originAccount.isEmpty() || destinationAccount.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(originAccount) == null){
            return new ResponseEntity<>("The origin account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(destinationAccount) == null){
            return new ResponseEntity<>("The destination account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (originAccount == destinationAccount){
            return new ResponseEntity<>("The account numbers can't be the same", HttpStatus.FORBIDDEN);
        }
        if (amount > account.getBalance()){
            return new ResponseEntity<>("Your balance isn't enough to make this transaction", HttpStatus.FORBIDDEN);
        }else {
            transactionService.saveTransaction(new Transaction(LocalDateTime.now(), amount, description, CREDIT, account2, currentBalanceCredit));
            transactionService.saveTransaction(new Transaction(LocalDateTime.now(), amount, description, DEBIT, account, currentBalanceDebit));
            account.setBalance(currentBalanceDebit);
            account2.setBalance(currentBalanceCredit);
            accountService.saveAccount(account);
            accountService.saveAccount(account2);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }
}

package com.MindHub.homebanking.controllers;
import com.MindHub.homebanking.dtos.AccountDTO;
import com.MindHub.homebanking.models.Account;
import com.MindHub.homebanking.models.AccountType;
import com.MindHub.homebanking.models.Client;
import com.MindHub.homebanking.models.Transaction;
import com.MindHub.homebanking.services.AccountService;
import com.MindHub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;


    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccountsDto();
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        return accountService.getAccount(id);
    }


    @RequestMapping("/clients/current/accounts/{id}")
    public AccountDTO getCurrentAccount(@PathVariable Long id,Authentication authentication){

        Client client =  clientService.findByMail(authentication.getName());
        Account currentAccount = accountService.findById(id);

        if(!client.getAccounts().contains(currentAccount))
        {
            return null;
        }

        return new AccountDTO(currentAccount);

    }


    @PostMapping(path= "/clients/current/accounts")
    public ResponseEntity<Object> createAccount (Authentication authentication,
                                                 @RequestParam AccountType accountType){

        Client client = clientService.findByMail(authentication.getName());
        Set<Account> clientAccounts = client.getAccounts().stream().filter(account -> account.isDisable() == false).collect(Collectors.toSet());

        if (clientAccounts.size() >= 3){
            return new ResponseEntity<>("You have reached the accounts limit", HttpStatus.FORBIDDEN);
        }

        accountService.saveAccount(new Account("VIN" + getRandomNumber(10000000, 99999999), LocalDateTime.now(), 0, client, false, accountType));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> disableAccount (Authentication authentication,
                                                  @RequestParam long id){


        Account disableAccount = accountService.findById(id);
        Client client =  clientService.findByMail(authentication.getName());

        if(!client.getAccounts().contains(disableAccount))
        {
            return new ResponseEntity<>("This account cannot be deleted because it's not yours", HttpStatus.FORBIDDEN);
        }

        disableAccount.setDisable(true);
        accountService.saveAccount(disableAccount);


        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
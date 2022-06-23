package com.MindHub.homebanking.services.implement;

import com.MindHub.homebanking.dtos.AccountDTO;
import com.MindHub.homebanking.models.Account;
import com.MindHub.homebanking.repositories.AccountRepository;
import com.MindHub.homebanking.services.AccountService;
import com.MindHub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientService clientService;

    @Override
    public List<AccountDTO> getAccountsDto() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }


    @Override
    public AccountDTO getAccount(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }


    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }


    @Override
    public AccountDTO getCurrentAccount(Long id, Authentication authentication) {
        return null;
    }


}

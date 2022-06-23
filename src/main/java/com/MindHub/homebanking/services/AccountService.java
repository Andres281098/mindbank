package com.MindHub.homebanking.services;

import com.MindHub.homebanking.dtos.AccountDTO;
import com.MindHub.homebanking.models.Account;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccountsDto();

    AccountDTO getAccount(@PathVariable Long id);

    void saveAccount(Account account);

    Account findById(Long id);

    Account findByNumber(String number);

    AccountDTO getCurrentAccount(@PathVariable Long id, Authentication authentication);



}

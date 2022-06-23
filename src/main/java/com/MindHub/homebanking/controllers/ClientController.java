package com.MindHub.homebanking.controllers;
import com.MindHub.homebanking.dtos.ClientDTO;
import com.MindHub.homebanking.models.Account;
import com.MindHub.homebanking.models.AccountType;
import com.MindHub.homebanking.models.Client;
import com.MindHub.homebanking.services.AccountService;
import com.MindHub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClientsDto();
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return clientService.getCurrentClient(authentication.getName());
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path= "/clients")
    public ResponseEntity<Object> register(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String mail,
            @RequestParam String password
    ){
        if(nombre.isEmpty() || apellido.isEmpty() || mail.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByMail(mail) != null){
            return new ResponseEntity<>("Mail already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(nombre, apellido, mail, passwordEncoder.encode(password));
        Account account = new Account("VIN" + getRandomNumber(10000000, 99999999), LocalDateTime.now(), 0, client, false, AccountType.SAVING);
        clientService.saveClient(client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient (Authentication authentication){
        return new ClientDTO (clientService.findByMail(authentication.getName()));
    }

    public int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
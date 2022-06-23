package com.MindHub.homebanking.dtos;


import com.MindHub.homebanking.models.Client;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private String nombre,apellido,mail;
    private long id;
    private Set<ClientLoanDTO> loans = new HashSet<>();
    private Set<AccountDTO> accounts = new HashSet<>();
    private Set<CardDTO> cards = new HashSet<>();

    public ClientDTO (){}

    public ClientDTO (Client client){
        this.nombre = client.getNombre();
        this.apellido = client.getApellido();
        this.mail = client.getMail();
        this.id = client.getId();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(toSet());
        this.loans = client.getClientLoan().stream().map(ClientLoanDTO::new).collect(toSet());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(toSet());
    }


    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMail() {
        return mail;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public long getId() {
        return id;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}

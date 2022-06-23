package com.MindHub.homebanking.services;

import com.MindHub.homebanking.dtos.ClientDTO;
import com.MindHub.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDto();

    ClientDTO getClient(@PathVariable Long id);

    ClientDTO getCurrentClient(String mail);


    Client findByMail(String mail);

    void saveClient(Client client);
}

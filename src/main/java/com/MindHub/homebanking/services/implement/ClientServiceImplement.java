package com.MindHub.homebanking.services.implement;


import com.MindHub.homebanking.dtos.ClientDTO;
import com.MindHub.homebanking.models.Client;
import com.MindHub.homebanking.repositories.ClientRepository;
import com.MindHub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClientsDto() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public ClientDTO getClient(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public Client findByMail(String mail) {
        return clientRepository.findByMail(mail);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }


    @Override
    public ClientDTO getCurrentClient(String mail) {
        return new ClientDTO(clientRepository.findByMail(mail));
    }
}

package com.example.uralsibtesttask.service.abstracts;

import com.example.uralsibtesttask.model.dto.ClientDto;
import com.example.uralsibtesttask.model.entity.Client;

import java.util.List;

public interface ClientService {
    void createClient(ClientDto clientDto);

    ClientDto findClient(long id);

    List<ClientDto> findAllClient();

    void deleteClient(long id);

    void updateClient(ClientDto clientDto);

}

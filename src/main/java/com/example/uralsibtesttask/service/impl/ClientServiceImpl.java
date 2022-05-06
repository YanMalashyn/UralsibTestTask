package com.example.uralsibtesttask.service.impl;

import com.example.uralsibtesttask.Exceptions.ClientException;
import com.example.uralsibtesttask.model.converters.ClientMapper;
import com.example.uralsibtesttask.model.dto.ClientDto;
import com.example.uralsibtesttask.model.entity.Client;
import com.example.uralsibtesttask.repository.ClientRepository;
import com.example.uralsibtesttask.service.abstracts.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientDto findClient(long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientException("Bank Client NOT Found"));
        return clientMapper.map(client);

    }

    @Override
    public void createClient(ClientDto clientDto) {
        clientRepository.save(clientMapper.map(clientDto));
    }

    @Override
    @Transactional
    public List<ClientDto> findAllClient() {
        return clientMapper.map(clientRepository.findAll());
    }

    @Override
    public void updateClient(ClientDto clientDto) {
        clientRepository.save(clientMapper.map(clientDto));
    }

    @Override
    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }

}

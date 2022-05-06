package com.example.uralsibtesttask.controller.rest;

import com.example.uralsibtesttask.model.dto.ClientDto;
import com.example.uralsibtesttask.service.abstracts.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/client")
@Slf4j
public class ClientResourceController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientDto clientDto) {
        clientService.createClient(clientDto);
        return new ResponseEntity<>("Bank client is created", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findClient(@PathVariable("id") long bankClientId) {
        ClientDto bankClient = clientService.findClient(bankClientId);
        return new ResponseEntity<>(bankClient, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@Valid @RequestBody ClientDto clientDto) {
        clientService.updateClient(clientDto);
        return new ResponseEntity<>("Bank Client is updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") long bankClientId) {
        clientService.deleteClient(bankClientId);
        return new ResponseEntity<>("Bank client is deleted", HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<ClientDto>> findAllClient() {
        List<ClientDto> allBankClient = clientService.findAllClient();
        return new ResponseEntity<>(allBankClient, HttpStatus.OK);
    }

}

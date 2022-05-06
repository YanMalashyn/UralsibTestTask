package com.example.uralsibtesttask.controller.rest;

import com.example.uralsibtesttask.model.dto.AccountDto;
import com.example.uralsibtesttask.service.abstracts.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/{client_id}/account")
public class AccountResourceController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@PathVariable("client_id") long clientId,
                                           @Valid @RequestBody AccountDto accountDto) {
        accountService.createAccount(clientId, accountDto);
        return new ResponseEntity<>("Bank Account is created", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findAccount(@PathVariable("id") long bankAccountId) {
        AccountDto bankAccount = accountService.findAccount(bankAccountId);
        return new ResponseEntity<>(bankAccount, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateAccount(@PathVariable("client_id") long clientId,
                                           @Valid @RequestBody AccountDto accountDto) {
        accountService.updateAccount(clientId, accountDto);
        return new ResponseEntity<>("Bank Account is updated", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> findAllAccount() {
        List<AccountDto> allBankAccount = accountService.findAllAccount();
        return new ResponseEntity<>(allBankAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") long bankAccountId) {
        accountService.deleteAccount(bankAccountId);
        return new ResponseEntity<>("Bank Account is deleted", HttpStatus.OK);
    }


}

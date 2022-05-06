package com.example.uralsibtesttask.controllers.rest;

import com.example.uralsibtesttask.service.abstracts.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operation")
public class BankOperationController {
    private final AccountService accountService;

    @PutMapping("/add/{id}")
    public ResponseEntity<?> addAmountToAccount(@PathVariable long id, @RequestBody BigDecimal bigDecimal) {
        accountService.addAmountToAccount(id, bigDecimal);
        return new ResponseEntity<>("Well done", HttpStatus.OK);
    }

    @PutMapping("/substract/{id}")
    public ResponseEntity<?> sybstractAmountFromAccount(@PathVariable long id, @RequestBody BigDecimal bigDecimal) {
        accountService.substractAmountFromAccount(id, bigDecimal);
        return new ResponseEntity<>("Well done", HttpStatus.OK);
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> transferAmount(@RequestParam("to") long bankAccountToId,
                                            @RequestParam("from") long bankAccountFromId,
                                            @RequestBody BigDecimal amount) {
        accountService.transfer(bankAccountToId, bankAccountFromId, amount);
        return new ResponseEntity<>("Well done", HttpStatus.OK);
    }

}

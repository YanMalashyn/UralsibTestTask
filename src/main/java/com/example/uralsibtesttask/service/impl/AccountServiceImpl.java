package com.example.uralsibtesttask.service.impl;

import com.example.uralsibtesttask.Exceptions.AccountException;
import com.example.uralsibtesttask.Exceptions.BankOperationException;
import com.example.uralsibtesttask.model.converters.AccountMapper;
import com.example.uralsibtesttask.model.dto.AccountDto;
import com.example.uralsibtesttask.model.entity.Account;
import com.example.uralsibtesttask.repository.AccountRepository;
import com.example.uralsibtesttask.repository.ClientRepository;
import com.example.uralsibtesttask.service.abstracts.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public void transfer(long accountToId, long accountFromId, BigDecimal amount) {
        substractAmountFromAccount(accountFromId, amount);
        addAmountToAccount(accountToId, amount);
    }

    public void addAmountToAccount(long accountId, BigDecimal amount) {
        AccountDto accountDto = findAccount(accountId);
        accountDto.setBalance(accountDto.getBalance().add(amount));
        updateAccount(accountDto.getClientId(), accountDto);
    }

    public void substractAmountFromAccount(long accountId, BigDecimal amount) {
        AccountDto account = findAccount(accountId);
        BigDecimal result = account.getBalance().subtract(amount);

        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new BankOperationException("Not enough amount");
        }
        account.setBalance(result);
        updateAccount(account.getClientId(), account);
    }


    @Override
    public AccountDto findAccount(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Bank Account NOT Found"));
        return accountMapper.map(account);
    }

    @Override
    public void createAccount(long clientId, AccountDto accountDto) {
        Account account = accountMapper.map(accountDto);
        account.setClient(clientRepository.getById(clientId));
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(long clientId, AccountDto accountDto){
        Account account = accountMapper.map(accountDto);
        account.setClient(clientRepository.getById(clientId));
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public List<AccountDto> findAllAccount() {
        return accountMapper.map(accountRepository.findAll());
    }

    @Override
    public void deleteAccount(long id){
        accountRepository.deleteById(id);
    }

}

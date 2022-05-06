package com.example.uralsibtesttask.service.abstracts;

import com.example.uralsibtesttask.model.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void createAccount(long clientId, AccountDto accountDto);

    AccountDto findAccount(long id);

    List<AccountDto> findAllAccount();

    void deleteAccount(long bankAccountId);

    void updateAccount(long clientId, AccountDto accountDto);

    void addAmountToAccount(long accountId, BigDecimal amount);

    void substractAmountFromAccount(long accountId, BigDecimal amount);

    void transfer(long accountToId, long accountFromId, BigDecimal amount);

}

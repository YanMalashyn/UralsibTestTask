package com.example.uralsibtesttask.model.converters;

import com.example.uralsibtesttask.model.dto.AccountDto;
import com.example.uralsibtesttask.model.entity.Account;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "client.id", target = "clientId")
    AccountDto map(Account account);
    Account map(AccountDto accountDto);
    List<AccountDto> map(List<Account> list);
}

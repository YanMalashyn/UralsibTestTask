package com.example.uralsibtesttask.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDto {
    private long id;
    private BigDecimal balance;
    private String currency;
    private long clientId;
}

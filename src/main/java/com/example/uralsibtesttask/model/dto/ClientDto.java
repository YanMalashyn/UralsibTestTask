package com.example.uralsibtesttask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClientDto {
    private long id;
    private String firstName;
    private String secondName;
    private List<AccountDto> accounts = new ArrayList<>();
}

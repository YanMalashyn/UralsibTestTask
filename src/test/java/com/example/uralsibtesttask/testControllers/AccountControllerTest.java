package com.example.uralsibtesttask.testControllers;

import com.example.uralsibtesttask.UralsibTestTaskApplication;
import com.example.uralsibtesttask.model.dto.AccountDto;
import com.example.uralsibtesttask.model.entity.Account;
import com.example.uralsibtesttask.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UralsibTestTaskApplication.class)
@AutoConfigureMockMvc
@DBUnit(caseSensitiveTableNames = true, cacheConnection = false, allowEmptyFields = true)
@TestPropertySource(properties = "test/resources/application.properties")
public class AccountControllerTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "dataset/accountcontroller.yml", cleanBefore = true, disableConstraints = true)
    public void createAccount() throws Exception {
        AccountDto accountDto = AccountDto.builder()
                .balance(new BigDecimal("100.22"))
                .currency("RU")
                .build();

        mockMvc.perform(
                        post("/client/100/account")
                                .content(new ObjectMapper().writeValueAsString(accountDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Account> op = accountRepository.findById(1L);
        assert (op.isPresent());
        assert (op.get().getBalance().compareTo(new BigDecimal("100.22")) == 0);
        assert (op.get().getCurrency().equals("RU"));
        assert (op.get().getClient().getId() == 100);
    }

    @Test
    @DataSet(value = "dataset/accountcontroller.yml", cleanBefore = true, disableConstraints = true)
    public void findAccount() throws Exception {
        mockMvc.perform(
                        get("/client/100/account/100")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.balance").value("100.0"))
                .andExpect(jsonPath("$.currency").value("RU"))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "dataset/accountcontroller.yml", cleanBefore = true, disableConstraints = true)
    public void updateAccount() throws Exception {
        AccountDto accountDto = AccountDto.builder()
                .id(100)
                .balance(new BigDecimal("1000"))
                .currency("EU")
                .build();

        mockMvc.perform(
                        put("/client/100/account")
                                .content(new ObjectMapper().writeValueAsString(accountDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/client/100/account/100")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.balance").value("1000.0"))
                .andExpect(jsonPath("$.currency").value("EU"))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "dataset/accountcontroller.yml", cleanBefore = true, disableConstraints = true)
    public void deleteAccount() throws Exception {
        mockMvc.perform(
                        delete("/client/100/account/100")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        assert (accountRepository.findById(100L).isEmpty());
    }

    @Test
    @DataSet(value = "dataset/accountcontroller.yml", cleanBefore = true, disableConstraints = true)
    public void findAllAccount() throws Exception {
        mockMvc.perform(
                        get("/client/100/account")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].balance").value("100.0"))
                .andExpect(jsonPath("$[1].id").value(101))
                .andExpect(jsonPath("$[1].balance").value("101.0"))
                .andExpect(jsonPath("$[2].id").value(102))
                .andExpect(jsonPath("$[2].balance").value("102.0"))
                .andExpect(status().isOk());

    }


}

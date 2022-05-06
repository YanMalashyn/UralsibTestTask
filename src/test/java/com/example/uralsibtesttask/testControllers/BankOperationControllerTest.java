package com.example.uralsibtesttask.testControllers;

import com.example.uralsibtesttask.UralsibTestTaskApplication;
import com.example.uralsibtesttask.model.entity.Account;
import com.example.uralsibtesttask.repository.AccountRepository;
import com.example.uralsibtesttask.repository.ClientRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UralsibTestTaskApplication.class)
@AutoConfigureMockMvc
@DBUnit(caseSensitiveTableNames = true, cacheConnection = false, allowEmptyFields = true)
@TestPropertySource(properties = "test/resources/application.properties")
public class BankOperationControllerTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "dataset/bankoperattioncontroller.yml", cleanBefore = true, disableConstraints = true)
    public void addAmountToAccount() throws Exception {

        mockMvc.perform(
                        put("/operation/add/{accountId}", 100)
                                .content(new ObjectMapper().writeValueAsString(new BigDecimal("100")))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Account account = accountRepository.findById(100L).get();
        assert (account.getBalance().compareTo(new BigDecimal("200.23")) == 0);

    }

    @Test
    @DataSet(value = "dataset/bankoperattioncontroller.yml", cleanBefore = true, disableConstraints = true)
    public void subsrtractAmountFromAccount() throws Exception {

        mockMvc.perform(
                        put("/operation/substract/{accountId}", 100)
                                .content(new ObjectMapper().writeValueAsString(new BigDecimal("100")))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Account account = accountRepository.findById(100l).get();
        assert (account.getBalance().compareTo(new BigDecimal("0.23")) == 0);
    }

    @Test
    @DataSet(value = "dataset/bankoperattioncontroller.yml", cleanBefore = true, disableConstraints = true)
    public void transfer() throws Exception {

        mockMvc.perform(
                        put("/operation/transfer?to=100&from=101")
                                .content(new ObjectMapper().writeValueAsString(new BigDecimal("50")))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Account accountTo = accountRepository.findById(100l).get();
        Account accountFrom = accountRepository.findById(101l).get();

        assert (accountTo.getBalance().compareTo(new BigDecimal("150.23")) == 0);
        assert (accountFrom.getBalance().compareTo(new BigDecimal("49.26")) == 0);
    }

    @Test
    @DataSet(value = "dataset/bankoperattioncontroller.yml", cleanBefore = true, disableConstraints = true)
    public void badTransfer() throws Exception {

        mockMvc.perform(
                        put("/operation/transfer?to=100&from=101")
                                .content(new ObjectMapper().writeValueAsString(new BigDecimal("200")))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        Account accountTo = accountRepository.findById(100l).get();
        Account accountFrom = accountRepository.findById(101l).get();

        assert (accountTo.getBalance().compareTo(new BigDecimal("100.23")) == 0);
        assert (accountFrom.getBalance().compareTo(new BigDecimal("99.26")) == 0);
    }
}

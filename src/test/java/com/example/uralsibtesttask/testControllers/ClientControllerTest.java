package com.example.uralsibtesttask.testControllers;

import com.example.uralsibtesttask.UralsibTestTaskApplication;
import com.example.uralsibtesttask.model.dto.ClientDto;
import com.example.uralsibtesttask.model.entity.Client;
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
public class ClientControllerTest {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DataSet(cleanBefore = true)
    public void createClient() throws Exception {
        ClientDto clientDto = ClientDto.builder()
                .firstName("Yan")
                .secondName("Malashyn")
                .build();
        mockMvc.perform(
                        post("/client/")
                                .content(new ObjectMapper().writeValueAsString(clientDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Optional<Client> bankClient = clientRepository.findById(1L);
        assert (bankClient.isPresent());
        assert (bankClient.get().getId() == 1);
        assert (bankClient.get().getFirstName().equals("Yan"));
        assert (bankClient.get().getSecondName().equals("Malashyn"));
    }

    @Test
    @DataSet(value = "dataset/clientcontroller.yml", cleanBefore = true)
    public void findClient() throws Exception {
        mockMvc.perform(
                        get("/client/100")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.firstName").value("Yan"))
                .andExpect(jsonPath("$.secondName").value("Malashyn"))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "dataset/clientcontroller.yml", disableConstraints = true, cleanBefore = true)
    public void updateClient() throws Exception {

        ClientDto clientDto = ClientDto.builder()
                .id(100)
                .firstName("Sergei")
                .secondName("Malashyn")
                .build();

        mockMvc.perform(
                        put("/client/")
                                .content(new ObjectMapper().writeValueAsString(clientDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/client/100")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.firstName").value("Sergei"))
                .andExpect(jsonPath("$.secondName").value("Malashyn"))
                .andExpect(status().isOk());


    }

    @Test
    @DataSet(value = "dataset/clientcontroller.yml", disableConstraints = true, cleanBefore = true)
    public void deleteClient() throws Exception {
        mockMvc.perform(
                        delete("/client/100")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        assert (clientRepository.findById(100L).isEmpty());
    }

    @Test
    @DataSet(value = "dataset/clientcontroller.yml", disableConstraints = true, cleanBefore = true)
    public void findAllClient() throws Exception {
        mockMvc.perform(
                        get("/client")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].firstName").value("Yan"))
                .andExpect(jsonPath("$[0].secondName").value("Malashyn"))
                .andExpect(jsonPath("$[1].id").value(101))
                .andExpect(jsonPath("$[1].firstName").value("Yan2"))
                .andExpect(jsonPath("$[1].secondName").value("Malashyn2"))
                .andExpect(jsonPath("$[2].id").value(102))
                .andExpect(jsonPath("$[2].firstName").value("Yan3"))
                .andExpect(jsonPath("$[2].secondName").value("Malashyn3"))
                .andExpect(status().isOk());
    }
}

package com.example.uralsibtesttask.model.converters;

import com.example.uralsibtesttask.model.dto.ClientDto;
import com.example.uralsibtesttask.model.entity.Client;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {AccountMapper.class})
public interface ClientMapper {
    Client map(ClientDto clientDto);
    ClientDto map(Client client);
    List<ClientDto> map(List<Client> list);
}

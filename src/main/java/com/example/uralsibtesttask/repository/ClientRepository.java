package com.example.uralsibtesttask.repository;

import com.example.uralsibtesttask.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    void deleteById(long id);
}

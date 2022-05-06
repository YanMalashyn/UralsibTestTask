package com.example.uralsibtesttask.repository;

import com.example.uralsibtesttask.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    void deleteById(long id);
}

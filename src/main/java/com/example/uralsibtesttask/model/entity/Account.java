package com.example.uralsibtesttask.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(generator = "account_seq")
    private long id;
    @Column(nullable = false)
    private BigDecimal balance;
    @Column(nullable = false)
    private String currency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @CreationTimestamp
    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime persistDateTime;
}

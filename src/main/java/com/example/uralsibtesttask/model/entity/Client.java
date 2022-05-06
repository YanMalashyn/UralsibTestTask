package com.example.uralsibtesttask.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(generator = "client_seq")
    private long id;
    private String firstName;
    private String secondName;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime persistDateTime;

}

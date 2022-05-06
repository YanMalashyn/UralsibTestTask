create sequence account_seq start 1 increment 1;
create sequence client_seq start 1 increment 1;

create table account
(
    id           int8           not null,
    balance      numeric(19, 2) not null,
    currency     varchar(255)   not null,
    persist_date timestamp,
    client_id    int8,
    primary key (id)
);

create table client
(
    id           int8 not null,
    first_name   varchar(255),
    persist_date timestamp,
    second_name  varchar(255),
    primary key (id)
);

alter table account
    add constraint FKkm8yb63h4ownvnlrbwnadntyn
    foreign key (client_id)
    references client;
--liquibase formatted sql

--changeset asoldatov:provider1
create table if not exists provider
(
    id           int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    name         varchar(64)                                          not null,
    guid         varchar(20)                                          not null unique,
    phone_number varchar(10)                                          not null,
    created_at   timestamp                                            not null,
    is_active    bool                                                 not null
);
--rollback drop table provider;
--comment: Создана таблица provider

--changeset asoldatov:provider2
create table if not exists merchant_provider
(
    merchant_id int8 NOT NULL,
    provider_id int8 NOT NULL,
    PRIMARY KEY (merchant_id, provider_id),
    CONSTRAINT merchant_provider_ibfk_1 FOREIGN KEY (merchant_id) REFERENCES merchant (id),
    CONSTRAINT merchant_provider_ibfk_2 FOREIGN KEY (provider_id) REFERENCES provider (id)
);
--rollback drop table merchant_provider;
--comment: Создана таблица merchant_provider

--changeset asoldatov:provider3
drop table if exists merchant_provider;
--comment: Удалена таблица merchant_provider

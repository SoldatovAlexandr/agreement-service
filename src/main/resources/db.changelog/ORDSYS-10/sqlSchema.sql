--liquibase formatted sql

--changeset asoldatov:agreement1
create table if not exists agreement
(
    id          int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    status      varchar(10)                                          NOT NULL,
    description varchar(256)                                         NOT NULL,
    created_at  timestamp with time zone                             NOT NULL,
    merchant_id int8                                                 NOT NULL,
    provider_id int8                                                 NOT NULL,
    CONSTRAINT agreement_ibfk_1 FOREIGN KEY (merchant_id) REFERENCES merchant (id),
    CONSTRAINT agreement_ibfk_2 FOREIGN KEY (provider_id) REFERENCES provider (id)
);
--rollback drop table agreement;
--comment: Создана таблица agreement

--changeset asoldatov:agreement2
create table if not exists agreement_history
(
    id           int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    status       varchar(10)                                          NOT NULL,
    description  varchar(256)                                         NOT NULL,
    created_at   timestamp with time zone                             NOT NULL,
    agreement_id int8                                                 NOT NULL,
    CONSTRAINT merchant_provider_ibfk_1 FOREIGN KEY (agreement_id) REFERENCES agreement (id)
);
--rollback drop table agreement_history;
--comment: Создана таблица agreement_history

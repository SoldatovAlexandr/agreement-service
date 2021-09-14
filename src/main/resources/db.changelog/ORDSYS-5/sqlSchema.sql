--liquibase formatted sql

--changeset asoldatov:merchant1
create sequence if not exists hibernate_sequence
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;
--rollback drop sequence hibernate_sequence;
--comment: Добавлен hibernate_sequence

--changeset asoldatov:merchant2
create table if not exists merchant
(
    id           int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    name         varchar(64)                                          not null,
    guid         varchar(20)                                          not null unique,
    phone_number varchar(10)                                          not null,
    is_active    bool                                                 not null
);
--rollback drop table merchant;
--comment: Создана таблица merchant
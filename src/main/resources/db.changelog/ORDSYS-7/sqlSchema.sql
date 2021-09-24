--liquibase formatted sql

--changeset akorzh:users1
create table if not exists users
(
    id       int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    login    varchar(64)                                          not null unique,
    password varchar(256)                                          not null
);
--rollback drop table users;
--comment: Создана таблица users

--changeset akorzh:users2
create table if not exists roles
(
    id   int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    name varchar(64)                                          not null unique
);
--rollback drop table roles;
--comment: Создана таблица roles

--changeset akorzh:users3
create table if not exists user_roles
(
    user_id int8 NOT NULL,
    role_id int8 NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_roles_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_roles_ibfk_2 FOREIGN KEY (role_id) REFERENCES roles (id)
);
--rollback drop table user_roles;
--comment: Создана таблица user_roles

--changeset akorzh:users4
insert into roles (name)
values ('ROLE_MANAGER'),
       ('ROLE_DIRECTOR');
--comment: Вставка ролей по-умолчанию

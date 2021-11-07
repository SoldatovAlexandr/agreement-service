--liquibase formatted sql

--changeset asoldatov:phone_number1
ALTER TABLE provider
ALTER
COLUMN phone_number TYPE VARCHAR (18);

--rollback alter table provider alter column phone_number type varchar(10);
--comment: Изменена длина phone_number с 10 до 18

--changeset asoldatov:phone_number2
ALTER TABLE merchant
ALTER
COLUMN phone_number TYPE VARCHAR (18);

--rollback alter table merchant alter column phone_number type varchar(10);
--comment: Изменена длина phone_number с 10 до 18

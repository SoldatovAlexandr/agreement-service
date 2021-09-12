Agreement-service
================


INSTALLATION
------------

1. Java 11
2. PostgreSQL 12
3. Maven

QUICK START
-----------

1. Создать локальную базу и пользователя.

         CREATE DATABASE agreement_datasource;
         CREATE USER agreement_user WITH PASSWORD '123456';

2. Постараться запомнить пароль и имя пользователя.
3. Перейти в Edit configurations и добавить параметры:
    * AGREEMENT_DATASOURCE_URL
    * AGREEMENT_DATASOURCE_USERNAME
    * AGREEMENT_DATASOURCE_PASSWORD

4. Попробовать запустить проект.

WHAT'S NEXT
-----------
[Попробуй сам](http://localhost:8081/)
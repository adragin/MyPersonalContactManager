-- выполнить, если не создана БД
-- CREATE DATABASE MyPersonalCpntactManager_DB;

-- USE MyPersonalCpntactManager_DB;

-- выполнить, если не создана Таблица Contacts
-- CREATE TABLE Contacts (
--     id varchar(36),
--     First_Name VARCHAR(128) NOT NULL,
--     Last_Name VARCHAR(128),
--     Email varchar (50),
--     Phone varchar(15),
--     Birth_Day DATE,
--     Address VARCHAR(128),
--     Photo varchar(100),
--     Create_Date TIMESTAMP,
--     Last_Update_Date TIMESTAMP
-- );

-- select * from Contacts;

-- если верхниве шаги были выполнены ранее, то для добавления поля  Last_Update_Date выполнить следующие запрос
-- 1) запрос на добавления нового поля Last_Update_Date в таблицу Contacts
-- ALTER TABLE Contacts
-- add column Last_Update_Date TIMESTAMP;
-- 2) обновление всех существующиех данных в БД (добавить в новое поле значение из Create_Date)
-- UPDATE Contacts
-- SET Last_Update_Date = Create_Date;

-- Создание таблиц User and User_Token в вашей БД
--CREATE TABLE User (
--    ID_USER varchar(36) primary key,
--    USER_ROLE boolean check (USER_ROLE in (0,1)) not null,
--    LOGIN varchar(128) not null unique,
--    USER_PASSWORD  varchar(50) not null,
--    USER_Name varchar(36) not null,
--    Create_Date TIMESTAMP,
--    Last_Update_Date TIMESTAMP
--);
--
--select * from User;

--CREATE TABLE User_Token (
--    TOKEN varchar(256),
--    USER_ID varchar(36),
--    CREATE_DATE TIMESTAMP,
--    LAST_UPDATE_DATE TIMESTAMP
--);















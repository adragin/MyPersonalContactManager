-- выполнить, если не создана БД
-- CREATE DATABASE MyPersonalCpntactManager_DB;

USE MyPersonalCpntactManager_DB;

-- выполнить, если не создана Таблица Contacts
CREATE TABLE Contacts
(
    id               varchar(36),
    First_Name       VARCHAR(128) NOT NULL,
    Last_Name        VARCHAR(128),
    Email            varchar(50),
    Phone            varchar(15),
    Birth_Day        DATE,
    Address          VARCHAR(128),
    Photo            varchar(100),
    Create_Date      TIMESTAMP,
    Last_Update_Date TIMESTAMP
);

select *
from Contacts;

-- Создание таблиц Users and Users_Token в вашей БД
CREATE TABLE Users
(
    User_Id          varchar(36) primary key,
    User_Role        boolean check (User_Role in (0, 1)) not null,
    Login            varchar(128)                        not null unique,
    Password         varchar(50)                         not null,
    User_Name        varchar(36)                         not null,
    Create_Date      TIMESTAMP,
    Last_Update_Date TIMESTAMP
);

-- Тестовые users
INSERT INTO users (User_Id, User_Name, Login, Password, User_Role, Create_Date, Last_Update_Date)
VALUES ('4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', 'Alex Doe', 'alexdoe@example.com', 'password123', TRUE, NOW(), NOW()),
       ('d2b2b67e-5b2f-40a8-8b5e-c92a5b70e4d1', 'Nata Smith', 'natasmith@example.com', 'mypassword', TRUE, NOW(),
        NOW()),
       ('f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d', 'Alice Johnson', 'alicejohnson@example.com', 'alicepass', FALSE, NOW(),
        NOW()),
       ('c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98', 'Henry Moore', 'henrymoore@example.com', 'henrypass', FALSE, NOW(),
        NOW());


select *
from Users;

CREATE TABLE Users_Token
(
    Token            varchar(256),
    User_Id          varchar(36),
    Create_Date      TIMESTAMP,
    Last_Update_Date TIMESTAMP
);

-- Тестовая запись Users_Token
INSERT INTO Users_Token (Token, User_Id, Create_Date, Last_Update_Date)
VALUES ('001{alexdoe@example.com|password123}', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', now(), now());
















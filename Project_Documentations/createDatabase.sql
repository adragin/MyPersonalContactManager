-- выполнить, для пересоздания стартового набора данных

-- если скрипт выполняете впервые - раскомментируйте БД с опечаткой и закомментируйте правильное название
-- drop database MyPersonalCpntactManager_DB;
drop database MyPersonalContactManager_DB;

CREATE DATABASE MyPersonalContactManager_DB;

USE MyPersonalContactManager_DB;

#  drop table сontacts_phones;
#  drop table сontacts;
# drop table users_token;
# drop table users;

-- Создание таблицы Users
CREATE TABLE users
(
  user_id          varchar(36) primary key, -- добавлен PK
  user_role        boolean check (user_role in (0, 1)) not null,
  login            varchar(128)                        not null unique,
  password         varchar(50)                         not null,
  user_name        varchar(36)                         not null,
  create_date      TIMESTAMP,
  last_update_date TIMESTAMP
);

-- Тестовые Users
INSERT INTO users (user_id, user_name, login, password, user_role, create_date, last_update_date)
VALUES ('4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', 'Alex Doe', 'alexdoe@example.com', 'password123', TRUE, NOW(), NOW()),
       ('d2b2b67e-5b2f-40a8-8b5e-c92a5b70e4d1', 'Nata Smith', 'natasmith@example.com', 'mypassword', TRUE, NOW(), NOW()),
       ('f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d', 'Alice Johnson', 'alicejohnson@example.com', 'alicepass', FALSE, NOW(), NOW()),
       ('c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98', 'Henry Moore', 'henrymoore@example.com', 'henrypass', FALSE, NOW(), NOW());

-- select * from Users;

-- Создание таблицы Users_Token
CREATE TABLE users_token
(
  token            varchar(256) unique,
  user_id          varchar(36), -- FK -> Users (User_Id)
  create_date      TIMESTAMP,
  last_update_date TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

-- Тестовая запись Users_Token
INSERT INTO Users_Token (token, user_id, create_date, last_update_date)
VALUES ('001{alexdoe@example.com|password123}', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', now(), now());

-- Таблица Contacts
CREATE TABLE contacts
(
  id               varchar(36) PRIMARY KEY, -- добавлена опция PK
  first_name       VARCHAR(128) NOT NULL,
  last_name        VARCHAR(128),
  email            varchar(50),
  -- Phone_Id      varchar(15),          -- удален , теперь они в таблице Users_Phones
  birth_day        DATE,
  address          VARCHAR(128),
  photo            varchar(100),
  owner_id         varchar(36),             -- новое поле, FK
  create_date      TIMESTAMP,
  last_update_date TIMESTAMP,
  FOREIGN KEY (owner_id) REFERENCES Users (user_id)
);

-- Тестовые контакты (учтены ранее создаваемые user'ы в качестве Owner_Id)
INSERT INTO contacts (id, first_name, last_name, email, birth_day, address, photo, owner_id, create_date, last_update_date)
VALUES ('52e61735-fcbf-481c-9134-edce167cb94e', 'New', 'TestContact', 'test@example.com', '1980-01-01', 'New York, USA',
        'https://i.imgur.com/yold5nS.png', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', '2024-08-26 23:16:53', '2024-08-26 23:16:53'),
       ('8c1a6e76-e831-43f4-b27b-f7e7c70a146f', 'New', 'TestContact', 'test@example.com', '1980-01-01', 'New York, USA',
        'https://i.imgur.com/yold5nS.png', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', '2024-08-26 23:19:48', '2024-08-26 23:19:48'),
       ('a55b821e-6004-11ef-8672-fc5ceea01bf6', 'John', 'Doe', 'john.doe@example.com', '1985-04-12', '123 Main St, Springfield',
        'https://i.imgur.com/yold5nS.png', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', '2024-08-21 23:30:54', '2024-08-21 23:30:54'),
       ('a55b9d7b-6004-11ef-8672-fc5ceea01bf6', 'Jane', 'Smith', 'jane.smith@example.com', '1990-07-24', '456 Elm St, Metropolis',
        'https://i.imgur.com/yold5nS.png', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', '2024-08-21 23:30:54', '2024-08-21 23:30:54'),
       ('a55ba262-6004-11ef-8672-fc5ceea01bf6', 'Alice', 'Johnson', 'alice.johnson@example.com', '1987-01-15',
        '789 Oak St, Gotham', 'https://i.imgur.com/yold5nS.png', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', '2024-08-21 23:30:54',
        '2024-08-21 23:30:54'),
       ('a55ba9a1-6004-11ef-8672-fc5ceea01bf6', 'Grace', 'Miller', 'grace.miller@example.com', '1993-09-10',
        '505 Walnut St, Atlantis', 'https://i.imgur.com/yold5nS.png', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d',
        '2024-08-21 23:30:54', '2024-08-21 23:30:54'),
       ('a55bc5d5-6004-11ef-8672-fc5ceea01bf6', 'Henry', 'Moore', 'henry.moore@example.com', '1986-02-19', '606 Ash St, Hub City',
        'https://i.imgur.com/yold5nS.png', '4b3b85f1-1a3e-4c29-9b59-74c891b4b35d', '2024-08-21 23:30:54', '2024-08-21 23:30:54'),
       ('d65914eb-60bd-11ef-8672-fc5ceea01bf6', 'Michael', 'Johnson', 'michael.johnson@example.com', '1978-11-05',
        '456 Elm St, Anytown', 'https://i.imgur.com/yold5nS.png', 'd2b2b67e-5b2f-40a8-8b5e-c92a5b70e4d1', '2024-08-22 21:36:33',
        '2024-08-22 21:36:33'),
       ('d65a8ea6-60bd-11ef-8672-fc5ceea01bf6', 'Emily', 'Davis', 'emily.davis@example.com', '1992-03-21',
        '789 Oak Ave, Yourtown', 'https://i.imgur.com/yold5nS.png', 'd2b2b67e-5b2f-40a8-8b5e-c92a5b70e4d1', '2024-08-22 21:36:33',
        '2024-08-22 21:36:33'),
       ('d65a9997-60bd-11ef-8672-fc5ceea01bf6', 'David', 'Lee', 'david.lee@example.com', '1985-04-12', '123 Main St, Springfield',
        'https://i.imgur.com/yold5nS.png', 'd2b2b67e-5b2f-40a8-8b5e-c92a5b70e4d1', '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65a9a83-60bd-11ef-8672-fc5ceea01bf6', 'Olivia', 'Taylor', 'olivia.taylor@example.com', '1990-01-01',
        '456 Elm St, Anytown', 'https://i.imgur.com/yold5nS.png', 'd2b2b67e-5b2f-40a8-8b5e-c92a5b70e4d1', '2024-08-22 21:36:33',
        '2024-08-22 21:36:33'),
       ('d65a9aed-60bd-11ef-8672-fc5ceea01bf6', 'Benjamin', 'Hall', 'benjamin.hall@example.com', '1985-04-12',
        '123 Main St, Springfield', 'https://i.imgur.com/yold5nS.png', 'd2b2b67e-5b2f-40a8-8b5e-c92a5b70e4d1',
        '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65a9b71-60bd-11ef-8672-fc5ceea01bf6', 'Sophia', 'Allen', 'sophia.allen@example.com', '1992-03-21',
        '789 Oak Ave, Yourtown', 'https://i.imgur.com/yold5nS.png', 'd2b2b67e-5b2f-40a8-8b5e-c92a5b70e4d1', '2024-08-22 21:36:33',
        '2024-08-22 21:36:33'),
       ('d65a9be6-60bd-11ef-8672-fc5ceea01bf6', 'William', 'Wright', 'william.wright@example.com', '1985-04-12',
        '123 Main St, Springfield', 'https://i.imgur.com/yold5nS.png', 'f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d',
        '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65a9e81-60bd-11ef-8672-fc5ceea01bf6', 'Ava', 'Adams', 'ava.adams@example.com', '1990-01-01', '456 Elm St, Anytown',
        'https://i.imgur.com/yold5nS.png', 'f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d', '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65aafd3-60bd-11ef-8672-fc5ceea01bf6', 'James', 'King', 'james.king@example.com', '1985-04-12',
        '123 Main St, Springfield', 'https://i.imgur.com/yold5nS.png', 'f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d',
        '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65ab0ae-60bd-11ef-8672-fc5ceea01bf6', 'Charlotte', 'Scott', 'charlotte.scott@example.com', '1992-03-21',
        '789 Oak Ave, Yourtown', 'https://i.imgur.com/yold5nS.png', 'f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d', '2024-08-22 21:36:33',
        '2024-08-22 21:36:33'),
       ('d65ab10a-60bd-11ef-8672-fc5ceea01bf6', 'Ethan', 'Brown', 'ethan.brown@example.com', '1985-04-12',
        '123 Main St, Springfield', 'https://i.imgur.com/yold5nS.png', 'f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d',
        '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65ab178-60bd-11ef-8672-fc5ceea01bf6', 'Mia', 'Green', 'mia.green@example.com', '1990-01-01', '456 Elm St, Anytown',
        'https://i.imgur.com/yold5nS.png', 'f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d', '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65ab1df-60bd-11ef-8672-fc5ceea01bf6', 'Oliver', 'Baker', 'oliver.baker@example.com', '1985-04-12',
        '123 Main St, Springfield', 'https://i.imgur.com/yold5nS.png', 'f6c738a7-8d7d-4f1e-bb9c-d76d30b78c9d',
        '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65ab25c-60bd-11ef-8672-fc5ceea01bf6', 'Amelia', 'Nelson', 'amelia.nelson@example.com', '1992-03-21',
        '789 Oak Ave, Yourtown', 'https://i.imgur.com/yold5nS.png', 'c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98', '2024-08-22 21:36:33',
        '2024-08-22 21:36:33'),
       ('d65ab2ea-60bd-11ef-8672-fc5ceea01bf6', 'Noah', 'Hall', 'noah.hall@example.com', '1985-04-12', '123 Main St, Springfield',
        'https://i.imgur.com/yold5nS.png', 'c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98', '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65abbfa-60bd-11ef-8672-fc5ceea01bf6', 'Harper', 'Wright', 'harper.wright@example.com', '1990-01-01',
        '456 Elm St, Anytown', 'https://i.imgur.com/yold5nS.png', 'c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98', '2024-08-22 21:36:33',
        '2024-08-22 21:36:33'),
       ('d65abcd0-60bd-11ef-8672-fc5ceea01bf6', 'Elijah', 'Adams', 'elijah.adams@example.com', '1985-04-12',
        '123 Main St, Springfield', 'https://i.imgur.com/yold5nS.png', 'c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98',
        '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65abd55-60bd-11ef-8672-fc5ceea01bf6', 'Avery', 'King', 'avery.king@example.com', '1992-03-21', '789 Oak Ave, Yourtown',
        'https://i.imgur.com/yold5nS.png', 'c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98', '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65abdbb-60bd-11ef-8672-fc5ceea01bf6', 'Daniel', 'Scott', 'daniel.scott@example.com', '1985-04-12',
        '123 Main St, Springfield', 'https://i.imgur.com/yold5nS.png', 'c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98',
        '2024-08-22 21:36:33', '2024-08-22 21:36:33'),
       ('d65abe1f-60bd-11ef-8672-fc5ceea01bf6', 'Ella', 'Brown', 'ella.brown@example.com', '1990-01-01', '456 Elm St, Anytown',
        'https://i.imgur.com/yold5nS.png', 'c9e5d4f1-3e2a-472a-a09b-8c1b9bde1b98', '2024-08-22 21:36:33', '2024-08-22 21:36:33');

-- select * from Contacts;
-- drop table Contacts_Phones;

CREATE TABLE contacts_phones
(
  contact_id   varchar(36),             -- FK
  phone_number varchar(28) PRIMARY KEY, -- PK
  FOREIGN KEY (contact_id) REFERENCES contacts (id)
);

INSERT INTO Contacts_Phones (contact_id, phone_number)
VALUES ('52e61735-fcbf-481c-9134-edce167cb94e', '(213) 555-0173'),
       ('8c1a6e76-e831-43f4-b27b-f7e7c70a146f', '(310) 555-0937'),
       ('a55b821e-6004-11ef-8672-fc5ceea01bf6', '(415) 555-0841'),
       ('a55b821e-6004-11ef-8672-fc5ceea01bf6', '(646) 555-8888'),
       ('a55b821e-6004-11ef-8672-fc5ceea01bf6', '(212) 555-8888'),
       ('a55b9d7b-6004-11ef-8672-fc5ceea01bf6', '(646) 555-0238'),
       ('a55ba262-6004-11ef-8672-fc5ceea01bf6', '(212) 555-0156'),
       ('a55ba9a1-6004-11ef-8672-fc5ceea01bf6', '(718) 555-0465'),
       ('a55bc5d5-6004-11ef-8672-fc5ceea01bf6', '(323) 555-0190'),
       ('d65914eb-60bd-11ef-8672-fc5ceea01bf6', '(626) 555-0276'),
       ('d65a8ea6-60bd-11ef-8672-fc5ceea01bf6', '(408) 555-0342'),
       ('d65a9997-60bd-11ef-8672-fc5ceea01bf6', '(562) 555-0617'),
       ('d65a9a83-60bd-11ef-8672-fc5ceea01bf6', '(914) 555-0749'),
       ('d65a9aed-60bd-11ef-8672-fc5ceea01bf6', '(619) 555-0253'),
       ('d65a9b71-60bd-11ef-8672-fc5ceea01bf6', '(646) 555-0365'),
       ('d65a9be6-60bd-11ef-8672-fc5ceea01bf6', '(805) 555-0482'),
       ('d65a9e81-60bd-11ef-8672-fc5ceea01bf6', '(347) 555-0319'),
       ('d65aafd3-60bd-11ef-8672-fc5ceea01bf6', '(818) 555-0773'),
       ('d65ab0ae-60bd-11ef-8672-fc5ceea01bf6', '(510) 555-0551'),
       ('d65ab10a-60bd-11ef-8672-fc5ceea01bf6', '(303) 555-0644'),
       ('d65ab178-60bd-11ef-8672-fc5ceea01bf6', '(702) 555-0815'),
       ('d65ab1df-60bd-11ef-8672-fc5ceea01bf6', '(786) 555-0934'),
       ('d65ab25c-60bd-11ef-8672-fc5ceea01bf6', '(404) 555-0169'),
       ('d65ab2ea-60bd-11ef-8672-fc5ceea01bf6', '(215) 555-0297'),
       ('d65abbfa-60bd-11ef-8672-fc5ceea01bf6', '(713) 555-0540'),
       ('d65abcd0-60bd-11ef-8672-fc5ceea01bf6', '(216) 555-0726'),
       ('d65abd55-60bd-11ef-8672-fc5ceea01bf6', '(505) 555-0623'),
       ('d65abdbb-60bd-11ef-8672-fc5ceea01bf6', '(503) 555-0387'),
       ('d65abe1f-60bd-11ef-8672-fc5ceea01bf6', '(813) 555-0839');

-- select * from Contacts_Phones;


















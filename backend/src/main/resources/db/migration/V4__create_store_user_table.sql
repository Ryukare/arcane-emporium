CREATE TABLE store_user (
    id          integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username    varchar(20) NOT NULL UNIQUE,
    password    varchar(50) NOT NULL UNIQUE,
    email       varchar(50) NOT NULL UNIQUE,
    role        varchar(50) NOT NULL UNIQUE
);
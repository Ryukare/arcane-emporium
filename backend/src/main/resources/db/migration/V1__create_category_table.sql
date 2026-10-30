CREATE TABLE Category (
    id      integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name    varchar(50) NOT NULL UNIQUE
);
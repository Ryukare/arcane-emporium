CREATE TABLE Product (
    id      integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name    varchar(100) NOT NULL UNIQUE,
    description varchar(1000),
    stock integer,
    price float,
    category_id integer,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES Category(id)
);
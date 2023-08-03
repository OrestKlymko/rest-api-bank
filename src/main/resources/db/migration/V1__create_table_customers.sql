CREATE TABLE customers
(
    id   BIGINT      NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT,
    card_number VARCHAR(16) NOT NULL,
    code VARCHAR(4) NOT NULL
);

CREATE TABLE account
(   id BIGINT NOT NULL PRIMARY KEY ,
    balance             INT       NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
)
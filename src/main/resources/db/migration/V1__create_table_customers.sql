CREATE TABLE customers
(
    id   BIGINT      NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    card_number VARCHAR(16) NOT NULL,
    code VARCHAR(200) NOT NULL
);

CREATE TABLE account
(   id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    balance             INT       NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
)
CREATE TABLE account_history(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    value INT NOT NULL,
    transaction_history TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_type    ENUM ('Income','Spend'),
    account_id BIGINT,
    FOREIGN KEY (account_id) REFERENCES account(id)
)
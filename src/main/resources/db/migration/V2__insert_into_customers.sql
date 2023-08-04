INSERT INTO customers (card_number, code, name)
VALUES ('1111222233334444', '1234', 'Adam'),
       ('5555666677778888', '5678', 'John'),
       ('9999888877776666', '9876', 'Artem'),
       ('4444333322221111', '4321', 'Illyia'),
       ('1234123412341234', '8765', 'Orest');


INSERT INTO account ( balance, customer_id)
VALUES ( 1000, 1),
       (1000, 2),
       (1000, 3),
       (1000, 4),
       (1000, 5);

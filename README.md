
# REST API

This is a REST API, where you can find operation with bank account. It's small bank ecosystem with registration new customers, transactions and other features.




## Installation

1) Download the source code of the project from the GitHub repository.
2) Install dependencies, if any, using your programming language's package manager.
3) Run the project on your web server or cloud hosting, if necessary.

## Usage

1) Copy of this repository on your computer
2) Just start Spring

## Tech Stack


**Server:** Spring Boot, Spring MVC, Spring Data, Java 11, MySQL,  Flyway, Hibernate, Lombok, JDBC, JPA, Spring Security (Encoder), MockitoMVC, JUnit


## Authors

List the names of the authors who have contributed to the project and its maintenance.


## Feedback

If you have issues, questions, or suggestions, please create a new issue on GitHub, and we will do our best to assist you.



# Structure of block
```http
 {
        "id": 1,
        "cardNumber": "1111222233334444",
        "code": "1234",
        "account": {
            "id": 1,
            "balance": 1.0,
            "accountHistory": [
                {
                    "id": 6,
                    "value": 999.0,
                    "transaction_history": "2023-08-04",
                    "transactionType": "Spend"
                }
            ]
        }
    },
  ```
## API Reference

#### Get information about customers account

```http
  GET /api/v1/account/{id}/info 
```
Get information about the account and transaction history of the user by ID.

#### Get history of account

```http
  GET /api/v1/account/{id}/history
```
Get transaction history for a user account by their ID.

#### Create transaction between customers

```http
  POST /api/v1/user/transaction
```
Create a transaction, where you need to provide the sender's card data, receiver's information, and the amount in the request body.
Example body:
```http
{
    "fromCard":1111222233334444,
    "toCard":5555666677778888,
    "balance":999
}
```

#### login user with returns of boolean

```http
  POST /api/v1/user/login
```
User login endpoint, returns true or false. Example body:
```http
{
    "card_number":"3111222233334443",
    "code":1233
}
```

#### login user with returns of client info

```http
  POST /api/v1/user/login/client
``` 
User login endpoint, returns client information if the login is successful. Example body:
```http
{
    "card_number":"3111222233334443",
    "code":1233
}

You will get this body
{
    "id": 7,
    "cardNumer": "3111222233334443",
    "account": {
        "id": 7,
        "balance": 0,
        "accountHistory": []
    }
}

```

#### Register a new user

```http
  POST /api/v1/user/registration
```
Register a new user. Example body:
```http
{
    "name":"Orest",
    "code":"1235"
}
```

#### Get information about customer

```http
  GET /api/v1/user/client/{id}
```
Get all data about a user by their identifier.





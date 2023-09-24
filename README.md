# Bookstore

## Features

Design and implement a RESTful API for an online bookstore using Java and Spring Boot. This API should provide essential functionality for both the user and the bookstore admin.

## Tech

- Java 17
- Spring Boot
- JWT Token
- Postgresql
- Spring Security


## API Document
https://documenter.getpostman.com/view/29991894/2s9YJW5kZM

After the application starts running, a default ADMIN user is created, and this information is logged.

	Admin Token : Bearer <token>

Endpoints:

/users/** -> Users with any role
/books (GET) -> Users with any role
/books (PUT, UPDATE, DELETE) -> Requires an ADMIN token
/author/** -> Requires an ADMIN token
/orders/** -> Requires a USER/ADMIN token
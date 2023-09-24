package com.bookstore.bookstore.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserRequest {
    @Email
    String email;
    String password;
}

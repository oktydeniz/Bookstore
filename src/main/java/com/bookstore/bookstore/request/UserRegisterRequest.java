package com.bookstore.bookstore.request;

import com.bookstore.bookstore.enums.Role;
import lombok.Data;

@Data
public class UserRegisterRequest {
    String name;
    String email;
    String password;
    Role role;
}

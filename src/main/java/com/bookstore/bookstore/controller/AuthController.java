package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.request.UserRegisterRequest;
import com.bookstore.bookstore.request.UserRequest;
import com.bookstore.bookstore.response.AuthResponse;
import com.bookstore.bookstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest request) {
        return authService.login(request);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterRequest request) {
        return authService.register(request);
    }

}

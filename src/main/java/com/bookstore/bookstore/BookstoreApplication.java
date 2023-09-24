package com.bookstore.bookstore;

import com.bookstore.bookstore.enums.Role;
import com.bookstore.bookstore.request.UserRegisterRequest;
import com.bookstore.bookstore.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthService userService
    ) {
        return args -> {
            UserRegisterRequest user = new UserRegisterRequest();
            user.setName("Admin");
            user.setPassword("123456");
            user.setEmail("adminstore@bookstore.com");
            user.setRole(Role.ADMIN);
            String token = userService.registerForToken(user);
            System.out.println("\n\n\nAdmin Token : " + token + "\n\n");
        };
    }
}

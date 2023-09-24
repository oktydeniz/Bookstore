package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entitiy.User;
import com.bookstore.bookstore.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Modifying
    @Query(value = "SELECT u.id, u.name,u.email,u.role from users u where u.id =:id", nativeQuery = true)
    UserResponse findByUserId(Long id);
}

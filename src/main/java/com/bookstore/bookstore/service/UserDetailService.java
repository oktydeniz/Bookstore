package com.bookstore.bookstore.service;

import com.bookstore.bookstore.auth.JWTUserDetail;
import com.bookstore.bookstore.entitiy.User;
import com.bookstore.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user != null) {
            return JWTUserDetail.create(user);
        }
        throw new UsernameNotFoundException(email);
    }

    public UserDetails loadUserById(Long id) {
        User user = repository.findById(id).get();
        return JWTUserDetail.create(user);
    }
}

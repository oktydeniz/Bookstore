package com.bookstore.bookstore.auth;

import com.bookstore.bookstore.entitiy.User;
import com.bookstore.bookstore.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class JWTUserDetail implements UserDetails {

    Long id;
    private String userName;
    private String password;
    private String email;
    private Role role;
    private Collection<? extends GrantedAuthority> authorities;

    private JWTUserDetail(Long id, String userName, String password, String email, Role role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public static JWTUserDetail create(User user) {
        return new JWTUserDetail(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.authorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

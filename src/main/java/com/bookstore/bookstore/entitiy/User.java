package com.bookstore.bookstore.entitiy;

import com.bookstore.bookstore.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends DateBaseModel {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Getter
    @Setter
    @Column(name = "name")
    String name;

    @Getter
    @Setter
    @Column(name = "password")
    String password;

    @Getter
    @Setter
    @Column(name = "email", unique = true)
    String email;

    @Getter
    @Setter
    @JsonIgnore
    @Column(name = "role")
    Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Getter
    @JsonIgnore
    @Setter
    private List<Order> orders;
}

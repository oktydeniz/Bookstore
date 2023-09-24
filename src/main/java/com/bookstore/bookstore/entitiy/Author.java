package com.bookstore.bookstore.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends DateBaseModel {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Getter
    @Setter
    @Column(name = "author_name")
    String name;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
}

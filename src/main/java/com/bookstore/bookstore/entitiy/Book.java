package com.bookstore.bookstore.entitiy;

import com.bookstore.bookstore.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "books")
public class Book extends DateBaseModel {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long isbn;

    @Getter
    @Setter
    @Column(name = "title")
    String title;

    @Getter
    @Setter
    @Column(name = "price")
    BigDecimal price;

    @Getter
    @Setter
    @Column(name = "stock_quantity")
    Integer stockQuantity;

    @Getter
    @Setter
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private BookStatus status;

    @JsonIgnore
    @ManyToMany
    @Getter
    @Setter
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
}

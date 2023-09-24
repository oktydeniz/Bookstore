package com.bookstore.bookstore.response;

import com.bookstore.bookstore.entitiy.Author;
import lombok.Data;

@Data
public class BookAuthorResponse {
    Long id;
    String name;

    public BookAuthorResponse(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }
}

package com.bookstore.bookstore.response;

import com.bookstore.bookstore.entitiy.Author;
import com.bookstore.bookstore.entitiy.Book;
import lombok.Data;

import java.util.Set;

@Data
public class AuthorResponse {

    Long id;
    String authorName;
    Set<Book> books;

    public AuthorResponse(Author author) {
        this.authorName = author.getName();
        this.books = author.getBooks();
        this.id = author.getId();
    }
}

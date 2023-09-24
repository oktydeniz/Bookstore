package com.bookstore.bookstore.response;

import com.bookstore.bookstore.entitiy.Book;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookResponse {
    Long isbn;
    String title;
    BigDecimal price;
    List<BookAuthorResponse> authors;

    public BookResponse(Book book) {
        this.isbn = book.getIsbn();
        this.price = book.getPrice();
        this.title = book.getTitle();
        this.authors = book.getAuthors().stream().map(BookAuthorResponse::new).collect(Collectors.toList());

    }

}

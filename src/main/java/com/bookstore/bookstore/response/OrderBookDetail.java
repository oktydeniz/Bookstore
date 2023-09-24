package com.bookstore.bookstore.response;

import com.bookstore.bookstore.entitiy.Book;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderBookDetail {
    String title;
    BigDecimal price;

    public OrderBookDetail(Book book) {
        this.price = book.getPrice();
        this.title = book.getTitle();
    }
}

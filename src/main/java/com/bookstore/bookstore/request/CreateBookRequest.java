package com.bookstore.bookstore.request;

import com.bookstore.bookstore.enums.BookStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateBookRequest {

    String title;
    BigDecimal price;
    Integer stockQuantity;
    BookStatus bookStatus;
    List<Long> authorList;

}

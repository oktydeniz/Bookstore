package com.bookstore.bookstore.model;


import com.bookstore.bookstore.response.BookResponse;
import lombok.Data;

import java.util.List;

@Data
public class BooksPaginationModel {
    private List<BookResponse> books;
    private int pageNo;
    private int pageSize;
    private long totalElement;
    private boolean isLastPage;

}

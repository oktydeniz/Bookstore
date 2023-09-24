package com.bookstore.bookstore.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    List<Long> books;
}

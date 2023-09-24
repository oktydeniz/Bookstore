package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.entitiy.Book;
import com.bookstore.bookstore.model.ApiResponseModel;
import com.bookstore.bookstore.request.CreateBookRequest;
import com.bookstore.bookstore.response.BookResponse;
import com.bookstore.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping
    public ApiResponseModel<List<BookResponse>> books() {
        return service.books();
    }

    @GetMapping("/{isbn}")
    public ApiResponseModel<BookResponse> book(@PathVariable(required = true, value = "isbn") Long isbn) {
        return service.book(isbn);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ApiResponseModel<Book> saveBook(@RequestBody CreateBookRequest request) {
        return service.save(request);
    }

    @PutMapping("/{isbn}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponseModel<Book> updateBook(@PathVariable Long isbn, @RequestBody CreateBookRequest authorRequest) {
        return service.update(isbn, authorRequest);
    }

    @DeleteMapping("/{isbn}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ApiResponseModel<Boolean> deleteBook(@PathVariable(required = true, value = "isbn") Long isbn) {
        return service.delete(isbn);
    }
}

package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.ApiResponseModel;
import com.bookstore.bookstore.request.AuthorRequest;
import com.bookstore.bookstore.response.AuthorResponse;
import com.bookstore.bookstore.entitiy.Author;
import com.bookstore.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@PreAuthorize("hasRole('ADMIN')")
public class AuthorController {

    @Autowired
    AuthorService service;

    @GetMapping
    public ApiResponseModel<List<AuthorResponse>> authors() {
        return service.authors();
    }

    @PostMapping
    public ApiResponseModel<Author> saveAuthor(@RequestBody AuthorRequest request) {
        return service.saveAuthor(request);
    }

    @PutMapping("/{author_id}")
    public ApiResponseModel<Author> updateAuthor(@PathVariable(required = true, value = "author_id") Long authorId, @RequestBody AuthorRequest authorRequest) {
        return service.updateAuthor(authorId, authorRequest);
    }

    @DeleteMapping("/{author_id}")
    public ApiResponseModel<Boolean> deleteAuthor(@PathVariable(required = true, value = "author_id") Long authorId) {
        return service.deleteAuthor(authorId);
    }
}

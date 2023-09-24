package com.bookstore.bookstore.service;

import com.bookstore.bookstore.entitiy.Author;
import com.bookstore.bookstore.exception.EmptyFieldException;
import com.bookstore.bookstore.exception.NotFoundExceptionHandler;
import com.bookstore.bookstore.model.ApiResponseModel;
import com.bookstore.bookstore.repository.AuthorRepository;
import com.bookstore.bookstore.request.AuthorRequest;
import com.bookstore.bookstore.response.AuthorResponse;
import com.bookstore.bookstore.util.FormControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository repository;
    @Autowired
    MessageSource messageSource;

    public ApiResponseModel<Author> saveAuthor(AuthorRequest req) {
        Author isNameTaken = repository.findByName(req.getAuthorName());
        try {
            FormControl.validateField(req.getAuthorName());
        } catch (EmptyFieldException e) {
            return ApiResponseModel.create(e.getMessage(), false);
        }
        if (isNameTaken == null) {
            Author author = new Author();
            author.setName(req.getAuthorName());
            author.setCreatedAt(new Date());
            Author response = repository.save(author);

            return ApiResponseModel.create(response, message("author.created"), true);
        }
        return ApiResponseModel.create(message("author.exist"), true);
    }

    public ApiResponseModel<Boolean> deleteAuthor(Long authorId) {
        Author author = repository.findById(authorId).orElse(null);
        try {
            FormControl.objIsExist(author, "Author");
        } catch (NotFoundExceptionHandler e) {
            return ApiResponseModel.create(e.getMessage(), false);
        }
        repository.deleteById(authorId);
        return ApiResponseModel.create(message("author.deleted"), true);
    }

    public ApiResponseModel<Author> updateAuthor(Long authorId, AuthorRequest req) {
        Optional<Author> author = repository.findById(authorId);
        if (author.isPresent()) {
            try {
                FormControl.validateField(req.getAuthorName());
            } catch (EmptyFieldException e) {
                return ApiResponseModel.create(e.getMessage(), false);
            }
            Author _author = author.get();
            _author.setName(req.getAuthorName());
            _author.setCreatedAt(new Date());
            Author response = repository.save(_author);
            return ApiResponseModel.create(response, message("author.update.success"), true);
        }
        return ApiResponseModel.create(message("author.update.fail"), false);
    }

    public ApiResponseModel<List<AuthorResponse>> authors() {
        List<Author> authors = repository.findAll();
        if (!authors.isEmpty()) {
            List<AuthorResponse> authorResponses = authors.stream().map(AuthorResponse::new).collect(Collectors.toList());
            return ApiResponseModel.create(authorResponses, "", true);
        }
        return ApiResponseModel.create(message("error.basic"), false);
    }

    public String message(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }

}

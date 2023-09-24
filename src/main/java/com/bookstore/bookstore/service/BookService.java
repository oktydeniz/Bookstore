package com.bookstore.bookstore.service;

import com.bookstore.bookstore.entitiy.Author;
import com.bookstore.bookstore.entitiy.Book;
import com.bookstore.bookstore.enums.BookStatus;
import com.bookstore.bookstore.exception.EmptyFieldException;
import com.bookstore.bookstore.exception.NotFoundExceptionHandler;
import com.bookstore.bookstore.model.ApiResponseModel;
import com.bookstore.bookstore.model.BooksPaginationModel;
import com.bookstore.bookstore.repository.AuthorRepository;
import com.bookstore.bookstore.repository.BookRepository;
import com.bookstore.bookstore.request.CreateBookRequest;
import com.bookstore.bookstore.response.BookResponse;
import com.bookstore.bookstore.util.FormControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private MessageSource messageSource;

    public ApiResponseModel<BooksPaginationModel> books(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Book> pages = repository.findAll(pageable);
        //List<Book> bookList = repository.findAllByStatus();
        List<Book> listOfBooks = pages.getContent();
        List<BookResponse> content = listOfBooks.stream().map(BookResponse::new).collect(Collectors.toList());
        if (!listOfBooks.isEmpty()) {
            BooksPaginationModel model = new BooksPaginationModel();
            model.setBooks(content);
            model.setPageNo(pages.getNumber());
            model.setPageSize(pages.getSize());
            model.setTotalElement(pages.getTotalElements());
            model.setLastPage(pages.isLast());
            return ApiResponseModel.create(model, "", true);
        }
        return ApiResponseModel.create(message("error.basic"), false);
    }

    public ApiResponseModel<BookResponse> book(Long bookId) {
        Book book = repository.findById(bookId).orElse(null);
        if (book != null) {
            BookResponse response = new BookResponse(book);
            return ApiResponseModel.create(response);
        }
        return ApiResponseModel.create(message("book.not.found"), false);
    }

    public ApiResponseModel<Book> save(CreateBookRequest request) {
        try {
            FormControl.validateField(request.getTitle());
        } catch (EmptyFieldException e) {
            return ApiResponseModel.create(e.getMessage(), false);
        }
        Book book = new Book();
        book.setPrice(request.getPrice() == null ? new BigDecimal(0) : request.getPrice());
        book.setStatus(request.getBookStatus() == null ? BookStatus.INACTIVE : request.getBookStatus());
        book.setStockQuantity(request.getStockQuantity() == null ? 0 : request.getStockQuantity());
        book.setTitle(request.getTitle());
        List<Author> authors = authorRepository.findAuthorsById(request.getAuthorList());
        book.setAuthors(authors);
        Book savedBook = repository.save(book);
        return ApiResponseModel.create(savedBook, message("book.created"), true);
    }

    public ApiResponseModel<Boolean> delete(Long isbn) {
        Book book = repository.findById(isbn).orElse(null);
        try {
            FormControl.objIsExist(book, "Book");
        } catch (NotFoundExceptionHandler e) {
            return ApiResponseModel.create(e.getMessage(), false);
        }
        repository.deleteById(isbn);
        return ApiResponseModel.create(message("book.deleted"), true);
    }

    public ApiResponseModel<Book> update(Long isbn, CreateBookRequest req) {
        Optional<Book> book = repository.findById(isbn);
        try {
            FormControl.validateField(req.getTitle());
        } catch (EmptyFieldException e) {
            return ApiResponseModel.create(e.getMessage(), false);
        }
        if (book.isPresent()) {
            Book _book = book.get();
            _book.setPrice(req.getPrice() == null ? new BigDecimal(0) : req.getPrice());
            _book.setStatus(req.getBookStatus() == null ? BookStatus.INACTIVE : req.getBookStatus());
            _book.setStockQuantity(req.getStockQuantity() == null ? 0 : req.getStockQuantity());
            _book.setStatus(req.getBookStatus());
            List<Author> authors = authorRepository.findAuthorsById(req.getAuthorList());
            _book.setAuthors(authors);
            Book result = repository.save(_book);
            return ApiResponseModel.create(result, message("book.update.success"), true);
        }
        return ApiResponseModel.create(message("book.update.fail"), false);

    }

    public String message(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }

}

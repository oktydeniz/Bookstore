package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entitiy.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query(value = "SELECT * FROM books WHERE status = 1 ORDER BY created_at desc;", nativeQuery = true)
    public List<Book> findAllByStatus();

}

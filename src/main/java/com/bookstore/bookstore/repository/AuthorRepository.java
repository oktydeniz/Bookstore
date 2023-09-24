package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entitiy.Author;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Modifying
    @Query(value = "SELECT * FROM authors where authors.id in (:authors)", nativeQuery = true)
    public List<Author> findAuthorsById(List<Long> authors);

    public Author findByName(String name);
}

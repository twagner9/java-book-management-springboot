package com.twag.book_management_app.repository;

import java.util.List;
import com.twag.book_management_app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    public List<Book> findAllByOrderByTitleAsc();
    public List<Book> findAllByOrderByTitleDesc();
    public List<Book> findAllByOrderByAuthorAsc();
    public List<Book> findAllByOrderByAuthorDesc();
    public List<Book> findAllByOrderByGenreAsc();
    public List<Book> findAllByOrderByGenreDesc();
}

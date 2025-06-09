package com.twag.book_management_app.controller;

import java.util.List;
import com.twag.book_management_app.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.twag.book_management_app.repository.BookRepository;

@Controller
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        // This method would typically return a view or a list of books.
        // For simplicity, we are returning a string here.
        return bookRepository.findAll();
    }
}

package com.twag.book_management_app.service;

import java.util.List;

import com.twag.book_management_app.model.Book;
import com.twag.book_management_app.model.Catalog;
import com.twag.book_management_app.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    private final BookRepository bookRepo;

    public CatalogService(BookRepository bookRepository) {
        bookRepo = bookRepository;
    }

    public Catalog loadCatalogFromDatabase() {
        List<Book> books = bookRepo.findAll();
        return new Catalog(books, books.size());
    }
    
}

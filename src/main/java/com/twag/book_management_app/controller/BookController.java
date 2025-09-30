package com.twag.book_management_app.controller;

import java.util.List;
import com.twag.book_management_app.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.twag.book_management_app.repository.BookRepository;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
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

    @PostMapping
    public ResponseEntity<Book> addNewBook(@RequestBody Book newBook) {
        System.out.println("Executing addition of newBook...");
        return ResponseEntity.ok(bookRepository.save(newBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titleSortAsc") 
    public List<Book> getTitleSortedBooksAsc() { return bookRepository.findAllByOrderByTitleAsc(); }

    @GetMapping("/titleSortDesc")
    public List<Book> getTitleSortedBooksDesc() { return bookRepository.findAllByOrderByTitleDesc(); }
        
    @GetMapping("/authorLastSortAsc") 
    public List<Book> getAuthorLastSortedBooksAsc() { return bookRepository.findAllByOrderByAuthorLastAsc(); }

    @GetMapping("/authorLastSortDesc") 
    public List<Book> getAuthorLastSortedBooksDesc() { return bookRepository.findAllByOrderByAuthorLastDesc(); }

    @GetMapping("/genreSortAsc") 
    public List<Book> getGenreSortedBooksAsc() { return bookRepository.findAllByOrderByGenreAsc(); }

    @GetMapping("/genreSortDesc") 
    public List<Book> getGenreSortedBooksDesc() { return bookRepository.findAllByOrderByGenreDesc(); }
}

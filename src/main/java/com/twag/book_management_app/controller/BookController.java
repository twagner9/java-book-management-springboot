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

// import com.twag.book_management_app.repository.BookRepository;
import com.twag.book_management_app.repository.BookDatabase;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookDatabase bookDb;
    @Autowired
    public BookController(BookDatabase bookRepository) {
        this.bookDb = bookRepository;
    }

    @GetMapping
    public List<Book> getAll() {
        // This method would typically return a view or a list of books.
        // For simplicity, we are returning a string here.
        return bookDb.getAll();
    }

    @PostMapping
    public ResponseEntity<Book> insert(@RequestBody Book newBook) {
        System.out.println("Executing addition of newBook...");
        return ResponseEntity.ok(bookDb.insert(newBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
		// TODO: update this book existence check before the deletion operation
        if (!bookDb.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookDb.delete(id);
        return ResponseEntity.noContent().build();
    }

	// TODO: create each of these functions in the BookDatabase class
    @GetMapping("/titleSortAsc") 
    public List<Book> getTitleSortedBooksAsc() { return bookDb.findAllByOrderByTitleAsc(); }

    @GetMapping("/titleSortDesc")
    public List<Book> getTitleSortedBooksDesc() { return bookDb.findAllByOrderByTitleDesc(); }
        
    @GetMapping("/authorLastSortAsc") 
    public List<Book> getAuthorLastSortedBooksAsc() { return bookDb.findAllByOrderByAuthorLastAsc(); }

    @GetMapping("/authorLastSortDesc") 
    public List<Book> getAuthorLastSortedBooksDesc() { return bookDb.findAllByOrderByAuthorLastDesc(); }

    @GetMapping("/genreSortAsc") 
    public List<Book> getGenreSortedBooksAsc() { return bookDb.findAllByOrderByGenreAsc(); }

    @GetMapping("/genreSortDesc") 
    public List<Book> getGenreSortedBooksDesc() { return bookDb.findAllByOrderByGenreDesc(); }
}

package com.twag.book_management_app.controller;

import java.util.List;
import com.twag.book_management_app.model.Book;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.twag.book_management_app.repository.BookDatabase;

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
    public ResponseEntity<Boolean> insert(@RequestBody Book newBook) {
        System.out.println("Executing addition of newBook...");
        return ResponseEntity.ok(bookDb.insert(newBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
		// FIXME: check if this check is necessary, or if deletion can automatically be attempted
        if (!bookDb.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = bookDb.delete(id);

        // Return no content on success, but return an internal server error if the deletion was not performed
        // This is okay because the user should never be at a point where they can be deleting a book with an ID
        // that is present.
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/titleSortAsc") 
    public List<Book> getTitleSortedBooksAsc() { return bookDb.getAllBooksOrderAsc("title"); }

    @GetMapping("/titleSortDesc")
    public List<Book> getTitleSortedBooksDesc() { return bookDb.getAllBooksOrderDesc("title"); }
        
    @GetMapping("/authorLastSortAsc") 
    public List<Book> getAuthorLastSortedBooksAsc() { return bookDb.getAllBooksOrderAsc("author_last"); }

    @GetMapping("/authorLastSortDesc") 
    public List<Book> getAuthorLastSortedBooksDesc() { return bookDb.getAllBooksOrderDesc("author_last"); }

    @GetMapping("/genreSortAsc") 
    public List<Book> getGenreSortedBooksAsc() { return bookDb.getAllBooksOrderAsc("genre"); }

    @GetMapping("/genreSortDesc") 
    public List<Book> getGenreSortedBooksDesc() { return bookDb.getAllBooksOrderDesc("genre"); }
}

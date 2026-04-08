package com.twag.book_management_app.controller;

import java.util.List;
import com.twag.book_management_app.model.Book;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.twag.book_management_app.repository.BookDatabase;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookDatabase bookDb;

    @Autowired
    public BookController(BookDatabase bookRepository) {
        this.bookDb = bookRepository;
    }

    @GetMapping("/books")
    public List<Book> getAll() {
        // This method would typically return a view or a list of books.
        // For simplicity, we are returning a string here.
        return bookDb.getAll();
    }

    @GetMapping("/books/get/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookDb.getBookById(id);
    }

    @PostMapping("/books")
    public ResponseEntity<Integer> insert(@RequestBody Book newBook) {
        System.out.println("Executing addition of newBook...");
        int id = bookDb.insertAndReturnId(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    // Always tag data that must be sent with the function with RequestParam
    @PutMapping("/books/updateTitle/{id}")
    public ResponseEntity<Integer> updateTitle(@PathVariable Integer id, @RequestParam String newTitle) {
        System.out.println("Updating book title to: " + newTitle);
        int updatedId = bookDb.updateTitle(id, newTitle);
        return ResponseEntity.status(HttpStatus.OK).body(updatedId);
    }

    @PutMapping("/books/updateAuthorLast/{id}")
    public ResponseEntity<Integer> updateAuthorLast(@PathVariable Integer id, @RequestParam String newLast) {
        System.out.println("Updating book author_last to: " + newLast);
        int updatedId = bookDb.updateAuthorLast(id, newLast);
        return ResponseEntity.status(HttpStatus.OK).body(updatedId);
    }

    @PutMapping("/books/updateAuthorFirst/{id}")
    public ResponseEntity<Integer> updateFirstName(@PathVariable Integer id, @RequestParam String newFirst) {
        System.out.println("Updating book author_first to: " + newFirst);
        int updatedId = bookDb.updateauthorFirst(id, newFirst);
        return ResponseEntity.status(HttpStatus.OK).body(updatedId);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        // // FIXME: check if this check is necessary, or if deletion can automatically
        // be attempted
        // if (!bookDb.existsById(id)) {
        // return ResponseEntity.notFound().build();
        // }
        // boolean deleted = bookDb.delete(id);

        // // Return no content on success, but return an internal server error if the
        // deletion was not performed
        // // This is okay because the user should never be at a point where they can be
        // deleting a book with an ID
        // // that is present.
        // return deleted ? ResponseEntity.noContent().build() :
        // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        int rowsAffected = bookDb.delete(id.intValue());
        if (rowsAffected == 0) {
            return ResponseEntity.notFound().build(); // return 404
        }

        return ResponseEntity.noContent().build();
    }
    

    @GetMapping("/books/titleSortAsc")
    public List<Book> getTitleSortedBooksAsc() {
        return bookDb.getAllBooksOrderAsc("title");
    }

    @GetMapping("/books/titleSortDesc")
    public List<Book> getTitleSortedBooksDesc() {
        return bookDb.getAllBooksOrderDesc("title");
    }

    @GetMapping("/books/authorLastSortAsc")
    public List<Book> getAuthorLastSortedBooksAsc() {
        return bookDb.getAllBooksOrderAsc("author_last");
    }

    @GetMapping("/books/authorLastSortDesc")
    public List<Book> getAuthorLastSortedBooksDesc() {
        return bookDb.getAllBooksOrderDesc("author_last");
    }

    @GetMapping("/books/genreSortAsc")
    public List<Book> getGenreSortedBooksAsc() {
        return bookDb.getAllBooksOrderAsc("genre");
    }

    @GetMapping("/books/genreSortDesc")
    public List<Book> getGenreSortedBooksDesc() {
        return bookDb.getAllBooksOrderDesc("genre");
    }

    @PutMapping("/books/update_book/{id}")
    public ResponseEntity<Integer> updateBook(int id, String columnName, String columnUpdate) {
        int rowsAffected = bookDb.update(id, columnName, columnUpdate);
        if (rowsAffected == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}

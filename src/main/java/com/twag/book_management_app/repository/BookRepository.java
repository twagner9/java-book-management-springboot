package com.twag.book_management_app.repository;

import java.util.List;

import com.twag.book_management_app.model.Book; // FIXME: this will likely need to be a 
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}

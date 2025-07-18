package com.twag.book_management_app.repository_tests;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.twag.book_management_app.model.Book;
import com.twag.book_management_app.repository.BookRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest
public class BookRepositoryTests {
	@Autowired
	private BookRepository bookRepository;

	@BeforeAll
	void seedDatabase() {
		bookRepository.saveAll(List.of(
			new Book("Hello", "World", "Help", "Fantasy", 1),
			new Book("Zero", "Help", "Came", "Non-fiction", 100),
			new Book("Ascent", "Definitely", "Plagiarized", "Romance", 4)
		));
	}

	@Test
	public void testSortByTitleAsc() {

	}

	@Test
	public void testSortByTitleDesc() {
		
	}

	@Test
	public void testSortByAuthorLastAsc() {

	}

	@Test
	public void testSortByAuthorLastDesc() {
		
	}

	@Test
	public void testSortByGenreAsc() {

	}

	@Test
	public void testSortByGenreDesc() {
		
	}
}

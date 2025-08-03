package com.twag.book_management_app.repository_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	private Book sampleBook1 = new Book("Hello", "World", "Help", "Fantasy", 1);
	private Book sampleBook2 = new Book("Zero", "Help", "Came", "Non-fiction", 100);
	private Book sampleBook3 = new Book("Ascent", "Definitely", "Plagiarized", "Romance", 4);
	@BeforeAll
	void seedDatabase() {
		bookRepository.saveAll(List.of(sampleBook1, sampleBook2, sampleBook3));
	}

	@Test
	public void testSortByTitleAsc() {
		List<Book> sortedList = bookRepository.findAllByOrderByTitleAsc();
		assertEquals(sortedList.get(0), sampleBook3);
		assertEquals(sortedList.get(1), sampleBook1);
		assertEquals(sortedList.get(2), sampleBook2);
	}

	@Test
	public void testSortByTitleDesc() {
		List<Book> sortedList = bookRepository.findAllByOrderByTitleDesc();
		assertEquals(sortedList.get(0), sampleBook2);
		assertEquals(sortedList.get(1), sampleBook1);
		assertEquals(sortedList.get(2), sampleBook3);
	}

	@Test
	public void testSortByAuthorLastAsc() {
		List<Book> sortedList = bookRepository.findAllByOrderByAuthorLastAsc();
		assertEquals(sortedList.get(0), sampleBook3);
		assertEquals(sortedList.get(1), sampleBook2);
		assertEquals(sortedList.get(2), sampleBook1);
	}

	@Test
	public void testSortByAuthorLastDesc() {
		List<Book> sortedList = bookRepository.findAllByOrderByAuthorLastDesc();
		assertEquals(sortedList.get(0), sampleBook1);
		assertEquals(sortedList.get(1), sampleBook2);
		assertEquals(sortedList.get(2), sampleBook3);
	}

	@Test
	public void testSortByGenreAsc() {
		List<Book> sortedList = bookRepository.findAllByOrderByGenreAsc();
		assertEquals(sortedList.get(0), sampleBook1);
		assertEquals(sortedList.get(1), sampleBook2);
		assertEquals(sortedList.get(2), sampleBook3);
	}

	@Test
	public void testSortByGenreDesc() {
		List<Book> sortedList = bookRepository.findAllByOrderByGenreDesc();
		assertEquals(sortedList.get(0), sampleBook3);
		assertEquals(sortedList.get(1), sampleBook2);
		assertEquals(sortedList.get(2), sampleBook1);
	}
}

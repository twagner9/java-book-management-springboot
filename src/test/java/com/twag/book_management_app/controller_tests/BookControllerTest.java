package com.twag.book_management_app.controller_tests;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.twag.book_management_app.model.Book;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.ArrayList;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;

// SpringBootTest and AutoConfigureMockMvc provide application context, allow HTTP requests to endpoints.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
// @Transactional // Ensures tests run in isolation and clean up after
// themselves
@Testcontainers
public class BookControllerTest {
	// @Autowired
	// private MockMvc mockMvc;

	@LocalServerPort
	private Integer port;

	// Run tests is a dedicated SQL container instead of having to create a more
	// difficult to manage
	// database using psql
	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

	// private int id;

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost:" + port;
	}

	ArrayList<Integer> loadBooksForTest(List<Book> bookList) {
		ArrayList<Integer> ids = new ArrayList<>();
		bookList.forEach(book -> ids.add(
				RestAssured.given()
						.contentType(ContentType.JSON)
						.body(book)
						.post("/api/books")
						.then()
						.statusCode(201)
						.extract()
						.as(Integer.class)));
		return ids;
	}

	// @BeforeEach
	// public void setup() throws Exception {
	// MvcResult result = mockMvc.perform(post("/api/books")
	// .contentType(MediaType.APPLICATION_JSON)
	// .content(newBookJson))
	// .andExpect(status().isOk())
	// .andReturn();

	// String responseContent = result.getResponse().getContentAsString();
	// ObjectMapper objectMapper = new ObjectMapper();
	// JsonNode root = objectMapper.readTree(responseContent);
	// id = root.path("id").asInt();
	// }

	@Autowired
	private JdbcTemplate jdbc;

	@BeforeEach
	void resetDatabase() {
		jdbc.execute("DELETE FROM all_books");
	}

	@Test
	// @Sql("/insert_books.sql")
	public void testBookRetrieval() throws Exception {
		// mockMvc.perform(get("/api/books"))
		// .andExpect(status().isOk())
		// .andExpect(jsonPath("$[0].title").value("Test Book"));

		List<Book> bookList = List.of(
				new Book("Test Book", "lastname", "firstname", "Fantasy", 3),
				new Book("Test Two", "last", "first", "Fable", 1));

		loadBooksForTest(bookList);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/api/books")
				.then()
				.statusCode(200)
				.body(".", hasSize(2));

		// TODO: test the sorted orders
	}

	@Test
	// @Sql("/insert_books.sql")
	public void testBookDeletion() throws Exception {
		// mockMvc.perform(delete("/api/books/" + id))
		// .andExpect(status().isNoContent());
		List<Book> bookList = List.of(
				new Book("Test Book", "lastname", "firstname", "Fantasy", 3),
				new Book("Test Two", "last", "first", "Fable", 1));

		ArrayList<Integer> ids = loadBooksForTest(bookList);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.delete("/api/books/{id}", ids.get(0));

		RestAssured.given()
				.get("/api/books")
				.then()
				.statusCode(200)
				.body(".", hasSize(1));
		// mockMvc.perform(get("/api/books"))
		// .andExpect(status().isOk())
		// .andExpect(jsonPath("$").isEmpty());
	}

	@Test
	public void testBookUpdate() throws Exception {
		List<Book> bookList = List.of(
				new Book("Test Book", "lastname", "firstname", "Fantasy", 3),
				new Book("Test Two", "last", "first", "Fable", 1));

		ArrayList<Integer> ids = loadBooksForTest(bookList);
		int idToUpdate = ids.get(0);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.queryParam("newTitle", "Updated Title")
				.when()
				.put("/api/books/updateTitle/{id}", idToUpdate)
				.then()
				.statusCode(200);

		Book returnedBook = RestAssured.given()
				.contentType(ContentType.JSON)
				.get("/api/books/{id}")
				.then()
				.statusCode(200)
				.extract()
				.as(Book.class);
		assertEquals("Updated Title", returnedBook.getTitle());
	}
}

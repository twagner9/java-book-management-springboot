package com.twag.book_management_app.controller_tests;

import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.twag.book_management_app.model.Book;
import com.twag.book_management_app.repository.BookRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

// SpringBootTest and AutoConfigureMockMvc provide application context, allow HTTP requests to endpoints.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc 
// @Transactional // Ensures tests run in isolation and clean up after themselves
@Testcontainers
public class BookControllerTest {
    // @Autowired
    // private MockMvc mockMvc;

	@LocalServerPort
	private Integer port;

	// Run tests is a dedicated SQL container instead of having to create a more difficult to manage
	// database using psql
	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    private int id;

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

	@Autowired
	BookRepository bookRepo;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost:" + port;
		bookRepo.deleteAll(); // Clear out the db before each set of tests
	}

    // @BeforeEach
    // public void setup() throws Exception {
    //     MvcResult result = mockMvc.perform(post("/api/books")
    //                             .contentType(MediaType.APPLICATION_JSON)
    //                             .content(newBookJson))
    //                             .andExpect(status().isOk())
    //                             .andReturn();

    //     String responseContent = result.getResponse().getContentAsString();
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     JsonNode root = objectMapper.readTree(responseContent);
    //     id = root.path("id").asInt();
    // }

    @Test
	// @Sql("/insert_books.sql")
    public void testBookRetrieval() throws Exception {
        // mockMvc.perform(get("/api/books"))
        // .andExpect(status().isOk())
        // .andExpect(jsonPath("$[0].title").value("Test Book"));

		List<Book> bookList = List.of(
			new Book("Test Book", "lastname", "firstname", "Fantasy", 3),
			new Book("Test Two", "last", "first", "Fable", 1)
		);
		bookRepo.saveAll(bookList);

		RestAssured.given()
			.contentType(ContentType.JSON)
			.when()
			.get("/api/books")
			.then()
			.statusCode(200)
			.body(".", hasSize(2));
    }

    @Test 
	// @Sql("/insert_books.sql") 
	public void testBookDeletion() throws Exception {
        // mockMvc.perform(delete("/api/books/" + id))
            // .andExpect(status().isNoContent());
		List<Book> bookList = List.of(
			new Book("Test Book", "lastname", "firstname", "Fantasy", 3),
			new Book("Test Two", "last", "first", "Fable", 1)
		);
		bookRepo.saveAll(bookList);

		RestAssured.given()
			.contentType(ContentType.JSON)
			.when()
			.delete("/api/books/1")
			.then()
			.statusCode(204)
			.body(".", hasSize(2));
        // mockMvc.perform(get("/api/books"))
            // .andExpect(status().isOk())
            // .andExpect(jsonPath("$").isEmpty());
    }
}

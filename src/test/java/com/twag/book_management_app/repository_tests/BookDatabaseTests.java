package com.twag.book_management_app.repository_tests;

import com.twag.book_management_app.model.Book;
import com.twag.book_management_app.repository.BookDatabase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @ActiveProfiles("test")
@SpringBootTest
@Testcontainers
public class BookDatabaseTests {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    


    @Value("${spring.datasource.url")
    String url = "jdbc:postgresql://localhost:5432/testdb";

    @Value("${spring.datasource.user")
    String user = "testuser";

    @Value("${spring.datasource.password")
    String password = "testpassword";
    private final BookDatabase db;
    private Book sampleBook1 = new Book("Hello", "World", "Help", "Fantasy", 1);
	private Book sampleBook2 = new Book("Zero", "Help", "Came", "Non-fiction", 100);
	private Book sampleBook3 = new Book("Ascent", "Definitely", "Plagiarized", "Romance", 4);

    @Autowired
    BookDatabaseTests(BookDatabase db) {
        this.db = db;
    }

    @Test
    boolean testDatabaseConnection() {
        String url = "jdbc:postgresql://localhost:5432/testdb";
        String user = "icpostgresql";
        String password = "testpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Connected to PostgreSQL server successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Connection to PostgreSQL server failed");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Test
    boolean testDatabaseInsertion() {
        db.insertAndReturnId(sampleBook1);
        db.insertAndReturnId(sampleBook2);
        db.insertAndReturnId(sampleBook3);

        // Execute test fetch command
        List<Book> dbContents = db.getAll();
        assertEquals(sampleBook1, dbContents.get(0));
        assertEquals(sampleBook2, dbContents.get(1));
        assertEquals(sampleBook3, dbContents.get(2));
        return true;
    }

	@Test
	boolean testDatabaseDeletion() {
		db.delete(1);
		List<Book> dbContents = db.getAll();
		assertEquals(sampleBook2, dbContents.get(0));
		assertEquals(sampleBook3, dbContents.get(1));
		return true;
	}
}

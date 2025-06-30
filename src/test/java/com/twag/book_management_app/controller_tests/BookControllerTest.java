package com.twag.book_management_app.controller_tests;

import com.twag.book_management_app.model.Book;
import com.twag.book_management_app.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// SpringBootTest and AutoConfigureMockMvc provide application context, allow HTTP requests to endpoints.
@SpringBootTest
@AutoConfigureMockMvc 
@Transactional // Ensures tests run in isolation and clean up after themselves
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private BookRepository bookRepo;

    @Test
    public void testAddAndGetBooks() throws Exception {
        String newBookJson = """
                {
                    "title": "Test Book",
                    "authorLast": "lastname",
                    "authorFirst": "firstname",
                    "genre": "Fantasy",
                    "numCopies": 3
                }
                """;
            
        mockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newBookJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Test Book"));

        mockMvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Test Book"));
    }
}

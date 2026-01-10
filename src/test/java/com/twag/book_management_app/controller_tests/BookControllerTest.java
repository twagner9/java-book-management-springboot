package com.twag.book_management_app.controller_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twag.book_management_app.controller.BookController;
import com.twag.book_management_app.model.Book;
import com.twag.book_management_app.repository.BookDatabase;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// SpringBootTest and AutoConfigureMockMvc provide application context, allow HTTP requests to endpoints.
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookDatabase bookDb;

    private Book book;

    String newBookJson = """
        {
            "title": "Test Book",
            "authorLast": "lastname",
            "authorFirst": "firstname",
            "genre": "Fantasy",
            "numCopies": 3
        }
        """;

    private int id;

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
    @BeforeEach
    public void setup() {
        book = new Book("Test Book", "firstname", "lastname", "Fantasy", 3);
        when(bookDb.getAll()).thenReturn(new ArrayList<>(List.of(book)));
    }

    @Test
    public void testBookRetrieval() throws Exception {
        // NOTE: when statements are invoked when the  mockMvc test is performed, and makes the
        // test return the value specified. In the case of getAll, this means returning
        // 
        when(bookDb.getAll()).thenReturn(new ArrayList<>(List.of(book)));

        mockMvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test public void testBookDeletion_Success() throws Exception {
        when(bookDb.existsById(id)).thenReturn(true);
        when(bookDb.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/api/books/" + id))
            .andExpect(status().isNoContent());
        // mockMvc.perform(get("/api/books"))
        //     .andExpect(status().isOk())
        //     .andExpect(jsonPath("$").isEmpty());
    }

    @Test public void testBookDeletion_notFound() throws Exception {
        when(bookDb.existsById(2)).thenReturn(false);
        // when(bookDb.delete(2)).thenReturn(false);

        mockMvc.perform(delete("/api/books/2"))
            .andExpect(status().isNotFound());
    }
}

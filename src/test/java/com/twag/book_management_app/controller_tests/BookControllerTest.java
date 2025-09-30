package com.twag.book_management_app.controller_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// SpringBootTest and AutoConfigureMockMvc provide application context, allow HTTP requests to endpoints.
@SpringBootTest
@AutoConfigureMockMvc 
@Transactional // Ensures tests run in isolation and clean up after themselves
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

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

    @BeforeEach
    public void setup() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newBookJson))
                                .andExpect(status().isOk())
                                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseContent);
        id = root.path("id").asInt();
    }

    @Test
    public void testBookRetrieval() throws Exception {
        mockMvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test public void testBookDeletion() throws Exception {
        mockMvc.perform(delete("/api/books/" + id))
            .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }
}

package com.twag.book_management_app.controller_tests;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.twag.book_management_app.controller.BookController;
import com.twag.book_management_app.repository.BookDatabase;

// These annotations allow injection of MockMvc instance. @SpringBootTest creates the whole application context
// If only specific layers are wanted, @WebMvcTest can be used instead. Both cases sees Spring Boot automatically search for the main application class of the app,
// but this can be overridden if something different is desired.
@WebMvcTest(BookController.class)
public class BookManagementControllerTest {

    @Autowired
    private MockMvc mvc; // MockMvc comes from Spring Test; allows sending requests to the DispatcherServlet and make assertions about the result (what getHello is doing); does not work for serving files
    
    @MockitoBean
    private BookDatabase bookDb;

    // Converts HTML code to String that can have its contents parsed to find identifying characteristics, guaranteeing that the
    // correct page is being returned by this controller
    @Test
    public void getBooks_returnsBooks() throws Exception {
        when(bookDb.getAll()).thenReturn(new ArrayList<>());
        mvc.perform(get("/api/books"))
            .andExpect(status().isOk());
        // java.net.URI uri = new java.net.URI("http://localhost:" + port + "/");
        // java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        // uri.toURL().openStream().transferTo(baos);
        // String body = baos.toString();
        // org.assertj.core.api.Assertions.assertThat(body).contains("<title>");
    }
}

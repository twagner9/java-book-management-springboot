package com.twag.book_management_app.controller_tests;

// import static org.hamcrest.Matchers.equalTo;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// These annotations allow injection of MockMvc instance. @SpringBootTest creates the whole application context
// If only specific layers are wanted, @WebMvcTest can be used instead. Both cases sees Spring Boot automatically search for the main application class of the app,
// but this can be overridden if something different is desired.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookManagementControllerTest {

    // @Autowired
    // private MockMvc mvc; // MockMvc comes from Spring Test; allows sending requests to the DispatcherServlet and make assertions about the result (what getHello is doing); does not work for serving files
    @LocalServerPort
    private int port;

    // Converts HTML code to String that can have its contents parsed to find identifying characteristics, guaranteeing that the
    // correct page is being returned by this controller
    @Test
    public void getHello() throws Exception {
        java.net.URI uri = new java.net.URI("http://localhost:" + port + "/");
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        uri.toURL().openStream().transferTo(baos);
        String body = baos.toString();
        org.assertj.core.api.Assertions.assertThat(body).contains("<title>");
    }
}

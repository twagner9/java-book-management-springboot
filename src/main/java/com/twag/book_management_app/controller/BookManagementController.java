package com.twag.book_management_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Combines @Controller and @ResponseBody; these annotations return data instead of a view.
public class BookManagementController {

    @GetMapping("/") // Maps '/' to the index method; invoked from CLI, it will return pure text
    public String index() {
        return "Greetings from Spring Boot.";
    }    
}

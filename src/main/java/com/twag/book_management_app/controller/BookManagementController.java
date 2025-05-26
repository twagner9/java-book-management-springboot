package com.twag.book_management_app.controller;

import com.twag.book_management_app.model.Catalog;
import com.twag.book_management_app.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

// @RestController // Combines @Controller and @ResponseBody; these annotations return data instead of a view.
@Controller
public class BookManagementController {
    private final CatalogService catalogService;
    @GetMapping("/") // Maps '/' to the index method; invoked from CLI, it will return pure text
    public String index() {
        return "forward:/index.html";
    }

    @Autowired
    public BookManagementController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog")
    @org.springframework.web.bind.annotation.ResponseBody
    public Catalog getCatalog() {
        return catalogService.loadCatalogFromDatabase();
    }
}

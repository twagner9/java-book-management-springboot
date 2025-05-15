package com.twag.book_management_app.tests_package;

import com.twag.book_management_app.Utils.UtilsTests;
import com.twag.book_management_app.book.BookTests;
import com.twag.book_management_app.catalog.CatalogTests;
import com.twag.book_management_app.database.DatabaseTests;

import java.util.ArrayList;
import java.util.List;


public class Tests {
    public static final List<String> errorMessages = new ArrayList<>();

    public static void main (String[] args) {
        BookTests.bookTests();
        CatalogTests.catalogTests();
        UtilsTests.utilsTests();
        DatabaseTests.databaseTests();

        if (!errorMessages.isEmpty()) {
            System.err.println("There were errors during testing.");
            for (String msg : errorMessages) {
                System.err.println(msg);
            }
        } else {
            System.out.println("All tests passed successfully.");
        }
    }
}


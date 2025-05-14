package com.tests_package;

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


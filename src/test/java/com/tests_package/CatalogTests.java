package com.tests_package;

import com.main_package.Book;
import com.main_package.Catalog;

import static com.main_package.Assert.*;
import static com.tests_package.Tests.errorMessages;


public class CatalogTests {

    /**
     * Test capabilities of the main.Catalog class. All error messages will be
     * recorded, so that when the test is run, logging is done effectively.
     */
    public static void catalogTests() {
        Catalog testCat = new Catalog();
        // 1. Test adding a book to the catalog (also a test of the constructor)
        // a. Adding to empty catalog
        String testTitle = "Hello", testAuthor = "Me";
        int testId = 1, testCopies = 20;
        Book testBook = new Book(testId, testTitle, testAuthor, testCopies);
        try {
            testCat.addBook(testBook);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error adding Book to Catalog in CatalogTests.");
        }
        Book resultBook = testCat.getBookById(1);

        try {
            assertTrue(testBook.equals(resultBook), "Error in Catalog class when adding to an empty catalog. Expected Book " + testBook.getDetails() + "; actual: " + resultBook);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // b. Add more copies to existing title
        try {
            testCat.addBook(testBook);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error adding Book to Catalog in CatalogTests.");
        }
        int actualCopies = testCat.getBookById(testId).getNumCopies();
        try {
            assertEquals(2 * testCopies, actualCopies, "Error in Catalog class when adding existing title to catalog. Expected " + (2 * testCopies) + "; actual: " + actualCopies);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // c. TODO: Add a book with the same ID number (unhappy case)

        // 2. Test listing the catalog
        String expectedList = "ID #: " + testId + "; Title: " + testTitle + "; Author: " + testAuthor + "; # Copies: " + (2 * testCopies);
        String actualList = testCat.getCatalog();
        try {
            assertEquals(expectedList, actualList, "Error in Catalog class when listing the Catalog with one book. Expected " + expectedList + " got " + actualList);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // Check listing catalog with multiple books
        String testTitle2 = "Hehe", testAuthor2 = "Haha";
        int testId2 = 2, testCopies2 = 80;
        Book testBook2 = new Book(testId2, testTitle2, testAuthor2, testCopies2);

        try {
            testCat.addBook(testBook2);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error adding to Catalog in CatalogTests.");
        }
        String expectedList2 = expectedList + "\nID #: " + testId2 + "; Title: " + testTitle2 + "; Author: " + testAuthor2 + "; # Copies: " + testCopies2;
        actualList = testCat.getCatalog();
        try {
            assertEquals(expectedList2, actualList, "Error in Catalog class when listing the Catalog with multiple Books. Expected " + expectedList2 + " got " + actualList);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // 3. Test returning number of titles.
        int expectedCatalogTitles = 2, actualCatalogTitles = testCat.getNumTitles();
        try {
            assertEquals(expectedCatalogTitles, actualCatalogTitles, "Error in Catalog class when returning number of titles. Expected " + expectedCatalogTitles + " got " + actualCatalogTitles);
        } catch (AssertionError e) {
            System.err.println("Error adding to Catalog in CatalogTests.");
        }

        // 4. Check removing books from catalog
        // a. Remove all copies
        try {
            testCat.removeBook(testId2, testCopies2);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error removing from catalog in CatalogTests");
        }
        actualList = testCat.getCatalog();
        try {
            assertEquals(expectedList, actualList, "Error in Catalog class when removing all copies of a book. Expected listing " + expectedList + " got " + actualList);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // b. Remove some copies
        try {
            testCat.removeBook(testId, testCopies); // total copies should be 40; this should remove 20
        } catch (Catalog.CatalogException e) {
            System.err.println("Error removing from catalog in CatalogTests");
        }
        expectedList = "ID #: " + testId + "; Title: " + testTitle + "; Author: " + testAuthor + "; # Copies: " + (testCopies);
        actualList = testCat.getCatalog();
        try {
            assertEquals(expectedList, actualList, "Error in Catalog class when removing part of total copies of a book. Expected listing " + expectedList + " got " + actualList);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // c. Remove too many copies
        try {
            testCat.removeBook(testId, testCopies * 2); // try to remove 40 copies when there's 20
            errorMessages.add("Error in Catalog class when removing too many copies of a book. Expected Catalog.CatalogException, did not get one.");
        } catch (Catalog.CatalogException e) {
            // Here, we want an exception because the user is trying to remove too many books.
        }
    }
}

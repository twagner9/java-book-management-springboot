package com.twag.book_management_app.catalog;

import com.twag.book_management_app.model.Book;
import com.twag.book_management_app.model.Catalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static com.twag.book_management_app.model.Assert.*;


public class CatalogTests {
    private final Catalog testCat = new Catalog();
    private final String testTitle = "Hello", testAuthor = "Me", testTitle2 = "Hehe", testAuthor2 = "Haha";
    private final int testId = 1, testCopies = 20;
    private final int testId2 = 2, testCopies2 = 80;
    private String expectedList, actualList;

    @Test
    void testBookAdditions() {
        Book testBook = new Book(testId, testTitle, testAuthor, testCopies);
        try {
            testCat.addBook(testBook);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error adding Book to Catalog in CatalogTests.");
        }
        Book resultBook = testCat.getBookById(1);
        assertEquals(testBook, resultBook, "Error in Catalog class when adding to an empty catalog. Expected Book " + testBook.getDetails() + "; actual: " + resultBook);

        // b. Add more copies to existing title
        try {
            testCat.addBook(testBook);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error adding Book to Catalog in CatalogTests.");
        }

        int actualCopies = testCat.getBookById(testId).getNumCopies();
        assertEquals(2 * testCopies, actualCopies, "Error in Catalog class when adding existing title to catalog. Expected " + (2 * testCopies) + "; actual: " + actualCopies);

        // c. TODO: Add a book with the same ID number (unhappy case)
    }

    @Test
    void testListingCatalog() {
        try {
            testCat.addBook(new Book(testId, testTitle, testAuthor, 2 * testCopies));
        } catch (Catalog.CatalogException e) {
            System.err.println(e.getMessage());
        }
        expectedList = "ID #: " + testId + "; Title: " + testTitle + "; Author: " + testAuthor + "; # Copies: " + (2 * testCopies);
        actualList = testCat.getCatalog();
        assertEquals(expectedList, actualList, "Error in Catalog class when listing the Catalog with one book. Expected " + expectedList + " got " + actualList);

        // Check listing catalog with multiple books
        Book testBook2 = new Book(testId2, testTitle2, testAuthor2, testCopies2);

        try {
            testCat.addBook(testBook2);
        } catch (Catalog.CatalogException e) {
            System.err.println(e.getMessage());
        }
        String expectedList2 = expectedList + "\nID #: " + testId2 + "; Title: " + testTitle2 + "; Author: " + testAuthor2 + "; # Copies: " + testCopies2;
        actualList = testCat.getCatalog();
        assertEquals(expectedList2, actualList, "Error in Catalog class when listing the Catalog with multiple Books. Expected " + expectedList2 + " got " + actualList);
    }

    @Test
    void testReturningNumTitles() {
        try {
            testCat.addBook(new Book(testId, testTitle, testAuthor, testCopies));
            testCat.addBook(new Book(testId2, testTitle2, testAuthor2, testCopies2));
        } catch (Catalog.CatalogException e) {
            System.err.println(e.getMessage());
        }
        int expectedCatalogTitles = 2, actualCatalogTitles = testCat.getNumTitles();
        try {
            assertEquals(expectedCatalogTitles, actualCatalogTitles, "Error in Catalog class when returning number of titles. Expected " + expectedCatalogTitles + " got " + actualCatalogTitles);
        } catch (AssertionError e) {
            System.err.println("Error adding to Catalog in CatalogTests.");
        }
    }

    @Test
    void testRemovingBooks() {
        // 4. Check removing books from catalog
        // a. Remove all copies
        try {
            testCat.removeBook(testId2, testCopies2);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error removing from catalog in CatalogTests");
        }
        actualList = testCat.getCatalog();
        assertEquals(expectedList, actualList, "Error in Catalog class when removing all copies of a book. Expected listing " + expectedList + " got " + actualList);

        // b. Remove some copies
        try {
            testCat.removeBook(testId, testCopies); // total copies should be 40; this should remove 20
        } catch (Catalog.CatalogException e) {
            System.err.println("Error removing from catalog in CatalogTests");
        }
        expectedList = "ID #: " + testId + "; Title: " + testTitle + "; Author: " + testAuthor + "; # Copies: " + (testCopies);
        actualList = testCat.getCatalog();
        assertEquals(expectedList, actualList, "Error in Catalog class when removing part of total copies of a book. Expected listing " + expectedList + " got " + actualList);

        // c. Remove too many copies
        try {
            testCat.removeBook(testId, testCopies * 2); // try to remove 40 copies when there's 20
        } catch (Catalog.CatalogException e) {
            // TODO: Here, we want an exception because the user is trying to remove too many books.
        }
    }

    public static void catalogTests() {
        CatalogTests tests = new CatalogTests();
        tests.testBookAdditions();
        tests.testListingCatalog();
        tests.testReturningNumTitles();
        tests.testRemovingBooks();
    }
}

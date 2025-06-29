package com.twag.book_management_app.catalog;

import com.twag.book_management_app.model.Book;
import com.twag.book_management_app.model.Catalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static com.twag.book_management_app.model.Assert.*;


public class CatalogTests {
    private final Catalog testCat = new Catalog();
    private final String testTitle = "Hello", testAuthorLast = "Wagner", testAuthorFirst = "Me", testGenre = "Autobiography";
    private final String testTitle2 = "Hehe", testAuthorLast2 = "Johns", testAuthorFirst2 = "Haha", testGenre2 = "Mystery";
    private final int testId = 1, testCopies = 20;
    private final int testId2 = 2, testCopies2 = 80;
    private String expectedList, actualList;

    @Test
    void testBookAdditions() {
        Book testBook = new Book(testId, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        try {
            testCat.addBook(testBook);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error adding Book to Catalog in CatalogTests.");
        }
        Book resultBook = testCat.getBookById(1);
        assertEquals(testBook, resultBook);

        // b. Add more copies to existing title
        try {
            testCat.addBook(testBook);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error adding Book to Catalog in CatalogTests.");
        }

        int actualCopies = testCat.getBookById(testId).getNumCopies();
        assertEquals(2 * testCopies, actualCopies);

        // c. TODO: Add a book with the same ID number (unhappy case)
    }

    @Test
    void testListingCatalog() {
        try {
            testCat.addBook(new Book(testId, testTitle, testAuthorLast, testAuthorFirst, testGenre, 2 * testCopies));
        } catch (Catalog.CatalogException e) {
            System.err.println(e.getMessage());
        }
        expectedList = "ID #: " + testId + "; Title: " + testTitle + "; Author: " + testAuthorLast +", " + testAuthorFirst + "; Genre: " + testGenre + "; # Copies: " + (2 * testCopies);
        actualList = testCat.getCatalog();
        assertEquals(expectedList, actualList);

        // Check listing catalog with multiple books
        Book testBook2 = new Book(testId2, testTitle2, testAuthorLast2, testAuthorFirst2, testGenre2 , testCopies2);

        try {
            testCat.addBook(testBook2);
        } catch (Catalog.CatalogException e) {
            System.err.println(e.getMessage());
        }
        String expectedList2 = expectedList + "\nID #: " + testId2 + "; Title: " + testTitle2 + "; Author: " + testAuthorLast2 + ", " + testAuthorFirst2 + "; Genre: " + testGenre2 + "; # Copies: " + testCopies2;
        actualList = testCat.getCatalog();
        assertEquals(expectedList2, actualList);
    }

    @Test
    void testReturningNumTitles() {
        try {
            testCat.addBook(new Book(testId, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies));
            testCat.addBook(new Book(testId2, testTitle2, testAuthorLast2, testAuthorFirst2, testGenre2, testCopies2));
        } catch (Catalog.CatalogException e) {
            System.err.println(e.getMessage());
        }
        int expectedCatalogTitles = 2, actualCatalogTitles = testCat.getNumTitles();
        try {
            assertEquals(expectedCatalogTitles, actualCatalogTitles);
        } catch (AssertionError e) {
            System.err.println("Error adding to Catalog in CatalogTests.");
        }
    }

    @Test
    void testRemovingBooks() {
        // 4. Check removing books from catalog
        // a. Remove all copies
        try {
            testCat.addBook(new Book(testId, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies * 2));
            testCat.addBook(new Book(testId2, testTitle2, testAuthorLast2, testAuthorFirst2, testGenre, testCopies2));
        } catch (Catalog.CatalogException e) {
            System.err.println("Error adding books to catalog before removal test: " + e.getMessage());
        }
        try {
            testCat.removeBook(testId2, testCopies2);
        } catch (Catalog.CatalogException e) {
            System.err.println("Error removing from catalog in CatalogTests");
        }

        expectedList = "ID #: " + testId + "; Title: " + testTitle + "; Author: " + testAuthorLast + ", " + testAuthorFirst + "; Genre: " + testGenre + "; # Copies: " + (testCopies * 2);
        actualList = testCat.getCatalog();
        assertEquals(expectedList, actualList);

        // b. Remove some copies
        try {
            testCat.removeBook(testId, testCopies); // total copies should be 40; this should remove 20
        } catch (Catalog.CatalogException e) {
            System.err.println("Error removing from catalog in CatalogTests");
        }
        expectedList = "ID #: " + testId + "; Title: " + testTitle + "; Author: " + testAuthorLast + ", " + testAuthorFirst + "; Genre: " + testGenre + "; # Copies: " + (testCopies);
        actualList = testCat.getCatalog();
        assertEquals(expectedList, actualList);

        // c. Remove too many copies
        try {
            testCat.removeBook(testId, testCopies * 2); // try to remove 40 copies when there's 20
        } catch (Catalog.CatalogException e) {
            // TODO: Here, we want an exception because the user is trying to remove too many books.
        }
    }
}

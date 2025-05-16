package com.twag.book_management_app.book;

import com.twag.book_management_app.model.Book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static com.twag.book_management_app.model.Assert.*;

public class BookTests {
    private String testTitle, testAuthor, expectedDetails, returnDetails, updatedTitle, updatedAuthor;
    private int testId, testCopies, updatedId, updatedNumCopies;
    private Book testBook;


    @Test
    void testGetters() {
        testTitle = "Hello";
        testAuthor = "Me";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testTitle, testAuthor, testCopies);
        expectedDetails = "ID #: " + testId + "; " + "Title: " + testTitle + "; " + "Author: " + testAuthor + "; " + "# Copies: " + testCopies;

        // 1. Test getters
        int returnId = testBook.getBookId(), returnCopies = testBook.getNumCopies();
        String returnTitle = testBook.getTitle(), returnAuthor = testBook.getAuthor();
        returnDetails = testBook.getDetails();
        assertEquals(testId, returnId, "Error in main.Book class when getting ID. Expected ID of " + testId + "; actual: " + returnId);
        assertEquals(testTitle, returnTitle, "Error in main.Book class when getting title. Expected title " + testTitle + "; actual: " + returnTitle);
        assertEquals(testAuthor, returnAuthor, "Error in main.Book class when getting author. Expected author " + testAuthor + "; actual: " + returnAuthor);
        assertEquals(testCopies, returnCopies, "Error in main.Book class when getting num copies. Expected num copies " + testCopies + "; actual: " + returnCopies);
        assertEquals(expectedDetails, returnDetails, "Error in main.Book class when getting details string. Expected details " + expectedDetails + "; actual: " + returnDetails);
    }

    @Test
    void testSetters() {
        testTitle = "Hello";
        testAuthor = "Me";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testTitle, testAuthor, testCopies);
        expectedDetails = "ID #: " + testId + "; " + "Title: " + testTitle + "; " + "Author: " + testAuthor + "; " + "# Copies: " + testCopies;

        updatedId = 2;
        updatedNumCopies = 5;
        updatedTitle = "Goodbye";
        updatedAuthor = "Them";
        testBook.setId(updatedId);
        testBook.setTitle(updatedTitle);
        testBook.setAuthor(updatedAuthor);
        testBook.setNumCopies(updatedNumCopies);

        expectedDetails = "ID #: " + updatedId + "; " + "Title: " + updatedTitle + "; " + "Author: " + updatedAuthor + "; " + "# Copies: " + updatedNumCopies;
        returnDetails = testBook.getDetails();

        // Already tested getters; let's just check the details and avoid unneeded verbosity.
        assertEquals(expectedDetails, returnDetails, "Error in main.Book class after changing Book object with setters. Expected details " + expectedDetails + "; actual: " + returnDetails);
    }

    @Test
    void testEquals() {
        testTitle = "Hello";
        testAuthor = "Me";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testTitle, testAuthor, testCopies);

        Book equalsTestBook = new Book(testId + 1, testTitle, testAuthor, testCopies);
        testBook = new Book(testId, testTitle, testAuthor, testCopies);
        assertTrue(testBook.equals(equalsTestBook), "Error in main.Book class with Book.equals. main.Book.equals function should have returned true, but returned false.");

        // Test if not equal
        String notEqualsTitle = "Hehe", notEqualsAuthor = "Haha";
        int notEqualsId = 10;
        Book notEqualsTestBook = new Book(notEqualsId, notEqualsTitle, notEqualsAuthor, updatedNumCopies); // use same copies since it's not checked in equality condition
        assertFalse(testBook.equals(notEqualsTestBook), "Error in main.Book class with Book.equals. main.Book.equals function should have returned false, but returned true.");
    }
}

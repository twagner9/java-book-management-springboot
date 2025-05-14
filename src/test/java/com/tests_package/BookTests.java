package com.tests_package;

import com.main_package.Book;

import static com.main_package.Assert.*;
import static com.tests_package.Tests.errorMessages;

public class BookTests {
    public static void bookTests() {
        String testTitle = "Hello", testAuthor = "Me";
        int testId = 1, testCopies = 20;
        Book testBook = new Book(testId, testTitle, testAuthor, testCopies);
        String expectedDetails = "ID #: " + testId + "; " + "Title: " + testTitle + "; " + "Author: " + testAuthor + "; " + "# Copies: " + testCopies;

        // 1. Test getters
        int returnId = testBook.getBookId(), returnCopies = testBook.getNumCopies();
        String returnTitle = testBook.getTitle(), returnAuthor = testBook.getAuthor(), returnDetails = testBook.getDetails();
        try {
            assertEquals(testId, returnId, "Error in main.Book class when getting ID. Expected ID of " + testId + "; actual: " + returnId);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }
        try {
            assertEquals(testTitle, returnTitle, "Error in main.Book class when getting title. Expected title " + testTitle + "; actual: " + returnTitle);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }
        try {
            assertEquals(testAuthor, returnAuthor, "Error in main.Book class when getting author. Expected author " + testAuthor + "; actual: " + returnAuthor);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }
        try {
            assertEquals(testCopies, returnCopies, "Error in main.Book class when getting num copies. Expected num copies " + testCopies + "; actual: " + returnCopies);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }
        try {
            assertEquals(expectedDetails, returnDetails, "Error in main.Book class when getting details string. Expected details " + expectedDetails + "; actual: " + returnDetails);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // 2. Test setters
        int updatedId = 2, updatedNumCopies = 5;
        String updatedTitle = "Goodbye", updatedAuthor = "Them";
        testBook.setId(updatedId);
        testBook.setTitle(updatedTitle);
        testBook.setAuthor(updatedAuthor);
        testBook.setNumCopies(updatedNumCopies);

        expectedDetails = "ID #: " + updatedId + "; " + "Title: " + updatedTitle + "; " + "Author: " + updatedAuthor + "; " + "# Copies: " + updatedNumCopies;
        returnDetails = testBook.getDetails();

        // Already tested getters; let's just check the details and avoid unneeded verbosity.
        try {
            assertEquals(expectedDetails, returnDetails, "Error in main.Book class after changing Book object with setters. Expected details " + expectedDetails + "; actual: " + returnDetails);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // 3. Test equals
        Book equalsTestBook = new Book(updatedId, updatedTitle, updatedAuthor, updatedNumCopies);
        try {
            assertTrue(testBook.equals(equalsTestBook), "Error in main.Book class with Book.equals. main.Book.equals function should have returned true, but returned false.");
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // Test if not equal
        String notEqualsTitle = "Hehe", notEqualsAuthor = "Haha";
        int notEqualsId = 10;
        Book notEqualsTestBook = new Book(notEqualsId, notEqualsTitle, notEqualsAuthor, updatedNumCopies); // use same copies since it's not checked in equality condition
        try {
            assertFalse(testBook.equals(notEqualsTestBook), "Error in main.Book class with Book.equals. main.Book.equals function should have returned false, but returned true.");
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }
    }
}

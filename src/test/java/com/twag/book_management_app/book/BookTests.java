package com.twag.book_management_app.book;

import com.twag.book_management_app.model.Book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static com.twag.book_management_app.model.Assert.*;

public class BookTests {
    private String testTitle, testAuthorLast, testAuthorFirst, testGenre, expectedDetails, returnDetails, updatedTitle, updatedAuthorLast;
    private String updatedAuthorFirst, updatedGenre;
    private int testId, testCopies, updatedId, updatedNumCopies;
    private final int AUTHOR_LAST = 0, AUTHOR_FIRST = 1;
    private Book testBook;


    @Test
    void testGetters() {
        testTitle = "Hello";
        testAuthorLast = "Wagner";
        testAuthorFirst = "Me";
        testGenre = "Autobiography";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        expectedDetails = "ID #: " + testId + "; " + "Title: " + testTitle + "; " + "Author: " + testAuthorLast + ", " + testAuthorFirst + "; Genre: " + testGenre + "; # Copies: " + testCopies;

        // 1. Test getters
        int returnId = testBook.getBookId(), returnCopies = testBook.getNumCopies();
        String returnTitle = testBook.getTitle();
        String[] authorNames = testBook.getAuthorFull();
        returnDetails = testBook.getDetails();
        assertEquals(testId, returnId);
        assertEquals(testTitle, returnTitle);
        assertEquals(testAuthorLast, authorNames[AUTHOR_LAST]);
        assertEquals(testAuthorFirst, authorNames[AUTHOR_FIRST]);
        assertEquals(testCopies, returnCopies);
        assertEquals(expectedDetails, returnDetails);
    }

    @Test
    void testSetters() {
        testTitle = "Hello";
        testAuthorLast = "Wagner";
        testAuthorFirst = "Me";
        testGenre = "Autobiography";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        expectedDetails = "ID #: " + testId + "; " + "Title: " + testTitle + "; " + "Author: " + testAuthorLast + ", " + testAuthorFirst + "; Genre: " + testGenre + "# Copies: " + testCopies;

        updatedId = 2;
        updatedNumCopies = 5;
        updatedTitle = "Goodbye";
        updatedAuthorLast = "Johns";
        updatedAuthorFirst = "Them";
        updatedGenre = "Fantasy";
        testBook.setId(updatedId);
        testBook.setTitle(updatedTitle);
        testBook.setAuthor(updatedAuthorLast, updatedAuthorFirst);
        testBook.setGenre(updatedGenre);
        testBook.setNumCopies(updatedNumCopies);

        expectedDetails = "ID #: " + updatedId + "; " + "Title: " + updatedTitle + "; " + "Author: " + updatedAuthorLast + ", " + updatedAuthorFirst + "; Genre: " + updatedGenre + "; # Copies: " + updatedNumCopies;
        returnDetails = testBook.getDetails();

        // Already tested getters; let's just check the details and avoid unneeded verbosity.
        assertEquals(expectedDetails, returnDetails);
    }

    @Test
    void testEquals() {
        testTitle = "Hello";
        testAuthorLast = "Wagner";
        testAuthorFirst = "Me";
        testGenre = "Autobiography";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);

        Book equalsTestBook = new Book(testId + 1, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        testBook = new Book(testId, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        assertTrue(testBook.equals(equalsTestBook));

        // Test if not equal
        String notEqualsTitle = "Hehe", notEqualsAuthorLast = "Haha", notEqualsAuthorFirst = "boo", notEqualsGenre = "Mystery";
        int notEqualsId = 10;
        Book notEqualsTestBook = new Book(notEqualsId, notEqualsTitle, notEqualsAuthorLast, notEqualsAuthorFirst, notEqualsGenre, updatedNumCopies); // use same copies since it's not checked in equality condition
        assertFalse(testBook.equals(notEqualsTestBook));
    }
}

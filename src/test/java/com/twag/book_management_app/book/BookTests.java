package com.twag.book_management_app.book;

import com.twag.book_management_app.model.Book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static com.twag.book_management_app.model.Assert.*;

public class BookTests {
    private String testImgPath, testTitle, testAuthorLast, testAuthorFirst, testGenre, expectedDetails, returnDetails, updatedTitle, updatedAuthorLast;
    private String updatedAuthorFirst, updatedGenre, updatedImagePath;
    private int testId, testCopies, updatedId, updatedNumCopies;
    private final int AUTHOR_LAST = 0, AUTHOR_FIRST = 1;
    private Book testBook;


    @Test
    void testGetters() {
        testImgPath = "/sample/nonexistent/image.png";
        testTitle = "Hello";
        testAuthorLast = "Wagner";
        testAuthorFirst = "Me";
        testGenre = "Autobiography";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testImgPath, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        expectedDetails = "ID #: " + testId + "; " + "Title: " + testTitle + "; " + "Author: " + testAuthorLast + ", " + testAuthorFirst + "; Genre: " + testGenre + "; # Copies: " + testCopies + "; Image path: " + testImgPath;

        // 1. Test getters
        int returnId = testBook.getId(), returnCopies = testBook.getNumCopies();
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
        testImgPath = "/sample/nonexistent/image.png";
        testTitle = "Hello";
        testAuthorLast = "Wagner";
        testAuthorFirst = "Me";
        testGenre = "Autobiography";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testImgPath, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        expectedDetails = "ID #: " + testId + "; " + "Title: " + testTitle + "; " + "Author: " + testAuthorLast + ", " + testAuthorFirst + "; Genre: " + testGenre + "# Copies: " + testCopies + "; Image path: " + testImgPath;

        updatedId = 2;
        updatedNumCopies = 5;
        updatedTitle = "Goodbye";
        updatedAuthorLast = "Johns";
        updatedAuthorFirst = "Them";
        updatedGenre = "Fantasy";
        updatedImagePath = "/another/fake/path.png";
        testBook.setId(updatedId);
        testBook.setTitle(updatedTitle);
        testBook.setAuthor(updatedAuthorLast, updatedAuthorFirst);
        testBook.setGenre(updatedGenre);
        testBook.setNumCopies(updatedNumCopies);
        testBook.setImagePath(updatedImagePath);

        expectedDetails = "ID #: " + updatedId + "; " + "Title: " + updatedTitle + "; " + "Author: " + updatedAuthorLast + ", " + updatedAuthorFirst + "; Genre: " + updatedGenre + "; # Copies: " + updatedNumCopies + "; Image path: " + updatedImagePath;
        returnDetails = testBook.getDetails();

        // Already tested getters; let's just check the details and avoid unneeded verbosity.
        assertEquals(expectedDetails, returnDetails);
    }

    @Test
    void testEquals() {
        testImgPath = "/sample/nonexistent/image.png";
        testTitle = "Hello";
        testAuthorLast = "Wagner";
        testAuthorFirst = "Me";
        testGenre = "Autobiography";
        testId = 1;
        testCopies = 20;
        testBook = new Book(testId, testImgPath, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);

        Book equalsTestBook = new Book(testId + 1, testImgPath, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        testBook = new Book(testId, testImgPath, testTitle, testAuthorLast, testAuthorFirst, testGenre, testCopies);
        assertTrue(testBook.equals(equalsTestBook));

        // Test if not equal
        String notEqualsTitle = "Hehe", notEqualsAuthorLast = "Haha", notEqualsAuthorFirst = "boo", notEqualsGenre = "Mystery", notEqualsImagePath = "/not/real.png";
        int notEqualsId = 10;
        Book notEqualsTestBook = new Book(notEqualsId, notEqualsImagePath, notEqualsTitle, notEqualsAuthorLast, notEqualsAuthorFirst, notEqualsGenre, updatedNumCopies); // use same copies since it's not checked in equality condition
        assertFalse(testBook.equals(notEqualsTestBook));
    }
}

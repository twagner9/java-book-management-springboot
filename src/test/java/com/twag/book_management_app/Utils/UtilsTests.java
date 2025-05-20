package com.twag.book_management_app.Utils;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import com.twag.book_management_app.model.Utils;
import com.twag.book_management_app.model.Book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static com.twag.book_management_app.model.Assert.*;

public class UtilsTests {
    private String expectedString;
    private int expectedNum;

    @Test
    void testGetInt() {
        String simulatedInput = "8\n-80\n-12\n0\n0\n1\n80\n12\n11\n10\n";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(in);
        // 1. Test getting int from user.
        // a. Test getting integer in range
        expectedNum = 8;
        int actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        assertEquals(expectedNum, actualNum);

        // b. Test getting integer below range.
        expectedNum = 1;
        actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        assertEquals(expectedNum, actualNum);

        // c. Test getting integer above range.
        expectedNum = 10;
        actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        assertEquals(expectedNum, actualNum);
    }

    @Test
    void testGetString() {
        String simulatedInput = "    \s\n\t\nhello\n";
        ByteArrayInputStream in  =new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(in);
        // 2. Test getting string from user.
        expectedString = "hello";
        String actualString = Utils.getStringFromUser("Enter a string: ", scanner);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testCreateBook() {
        String simulatedInput = "hello\nhello\n10\n";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(in);
        // 3. Test creating a book from function.
        expectedString = "hello";
        expectedNum = 10;

        Book expectedBook = new Book(expectedString, expectedString, expectedNum);
        Book actualBook = Utils.receiveBookInput(scanner);

        assertTrue(expectedBook.equals(actualBook));
    }
}

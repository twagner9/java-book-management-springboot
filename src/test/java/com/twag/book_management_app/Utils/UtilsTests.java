package com.twag.book_management_app.Utils;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import com.twag.book_management_app.model.Utils;
import com.twag.book_management_app.model.Book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import static com.twag.book_management_app.model.Assert.*;

public class UtilsTests {
    private final String fullSimulatedInput = """
                8
                -80
                -12
                0
                1
                80
                12
                11
                10
                   \s
                \t
                hello
                hello
                hello
                10
               \s""";
    private final ByteArrayInputStream in = new ByteArrayInputStream(fullSimulatedInput.getBytes());
    private final Scanner scanner = new Scanner(in);

    private String expectedString;
    private int expectedNum;

    @Test
    void testGetInt() {
        // 1. Test getting int from user.
        // a. Test getting integer in range
        expectedNum = 8;
        int actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        assertEquals(expectedNum, actualNum, "Error in Utils class: expected getIntFromUser to return " + expectedNum +"; got " + actualNum);

        // b. Test getting integer below range.
        expectedNum = 1;
        actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        assertEquals(expectedNum, actualNum, "Error in Utils class when entering values below lower bound. Expected " + expectedNum + "; got " + actualNum);

        // c. Test getting integer above range.
        expectedNum = 10;
        actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        assertEquals(expectedNum, actualNum, "Error in Utils class when entering values below lower bound. Expected " + expectedNum + "; got " + actualNum);
    }

    @Test
    void testGetString() {
        // 2. Test getting string from user.
        expectedString = "hello";
        String actualString = Utils.getStringFromUser("Enter a string: ", scanner);
        assertEquals(expectedString, actualString, "Error in Utils class when entering a String value. Expected '" + expectedString + "''; got '" + actualString + "'");
    }

    @Test
    void testCreateBook() {
        // 3. Test creating a book from function.
        Book expectedBook = new Book(expectedString, expectedString, expectedNum);
        Book actualBook = Utils.receiveBookInput(scanner);
        assertEquals(expectedBook, actualBook, "Error in Utils class when getting info to make Book. Expected '" + expectedBook.getDetails() + "'; got '" + actualBook + "'");
    }
}

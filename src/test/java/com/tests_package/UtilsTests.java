package com.tests_package;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import com.twag.book_management_app.model.Utils;
import com.twag.book_management_app.model.Book;

import static com.twag.book_management_app.model.Assert.*;
import static com.tests_package.Tests.errorMessages;

// TODO: I can actually put all of the simulated input into a single line at the start of the tests, so that I don't have to constantly create new
// Scanner objects for every test; this will save memory and make the test more efficient
public class UtilsTests {
    public static void utilsTests () {

        // Set up all of the simulated input values now, so that we only initialize one scanner.
        final String fullSimulatedInput = """
                8
                -80
                -12
                0
                1
                80
                12
                11
                10
                    
                \t
                hello
                hello
                hello
                10
                """;
        ByteArrayInputStream in = new ByteArrayInputStream(fullSimulatedInput.getBytes());
        Scanner scanner = new Scanner(in);


        // 1. Test getting int from user.
        // a. Test getting integer in range
        int expectedNum = 8;
        int actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        try {
            assertEquals(expectedNum, actualNum, "Error in Utils class: expected getIntFromUser to return " + expectedNum +"; got " + actualNum);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // b. Test getting integer below range.
        expectedNum = 1;
        actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        try {
            assertEquals(expectedNum, actualNum, "Error in Utils class when entering values below lower bound. Expected " + expectedNum + "; got " + actualNum);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // c. Test getting integer above range.
        expectedNum = 10;
        actualNum = Utils.getIntFromUser("Enter a number from 1 to 10: ", 1, 10, scanner);
        try {
            assertEquals(expectedNum, actualNum, "Error in Utils class when entering values below lower bound. Expected " + expectedNum + "; got " + actualNum);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }


        // 2. Test getting string from user.
        String expectedString = "hello";
        String actualString = Utils.getStringFromUser("Enter a string: ", scanner);
        try {
            assertEquals(expectedString, actualString, "Error in Utils class when entering a String value. Expected '" + expectedString + "''; got '" + actualString + "'");
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }

        // 3. Test creating a book from function.
        Book expectedBook = new Book(expectedString, expectedString, expectedNum);
        Book actualBook = Utils.receiveBookInput(scanner);
        try {
            assertEquals(expectedBook, actualBook, "Error in Utils class when getting info to make Book. Expected '" + expectedBook.getDetails() + "'; got '" + actualBook + "'");
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }
    }
}

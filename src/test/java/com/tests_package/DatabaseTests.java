package com.tests_package;

import com.main_package.Database;
import com.main_package.Catalog.CatalogException;
import com.main_package.Catalog;
import com.main_package.Book;

import static com.tests_package.Tests.errorMessages;
import static com.main_package.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;


public class DatabaseTests {
    public static void databaseTests() {
        // 1. Test opening a database
        String testUrl = "jdbc:sqlite:sample.db";
        // Create sample database, fill it with test data, then call load database.        
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Class org.sqlite.JDBC not found.");
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(testUrl);
            if (conn != null) {
                String tableCreation = "CREATE TABLE IF NOT EXISTS catalog ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL,"
                + "author TEXT NOT NULL,"
                + "copies INTEGER NOT NULL"
                + ");";
                Statement sqlStatement = conn.createStatement();
                sqlStatement.execute(tableCreation);

                // Created; now let's insert a new book object
                String queryToExecute = """
                                            INSERT OR REPLACE INTO catalog (id, title, author, copies) VALUES(?, ?, ?, ?) \
                                            ON CONFLICT(id) DO UPDATE SET title = excluded.title, author = excluded.author, \
                                            copies = excluded.copies;\
                                        """;
                PreparedStatement pstmt = conn.prepareStatement(queryToExecute);
                pstmt.setInt(1, 1);
                pstmt.setString(2, "Hello");
                pstmt.setString(3, "World");
                pstmt.setInt(4, 10);

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error in DatabaseTests when creating sample database " + testUrl);
            System.err.println(e.getMessage());
        }

        Catalog testCat = Database.loadDatabase(testUrl);
        // Let's try listing the catalog:
        String expectedCatalogList = "ID #: 1; Title: Hello; Author: World; # Copies: 10";
        String actualCatalogList = testCat.getCatalog();
        try {
            assertEquals(expectedCatalogList, actualCatalogList, "Error reading database from SQL file. Expected catalog list " + expectedCatalogList + "; got " + actualCatalogList);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }
        // Have a database containing a single item; let's read it in via the 
        // TODO: 2. Test saving a Catalog to the database
        try {
            testCat.addBook(new Book(2, "Test", "Book", 2));
        } catch (CatalogException e) {
            System.err.println("In DatabaseTests, error adding second sample book to database. Write will be invalid.\n" + e.getMessage());
        }

        // NOTE: Up to here, the in memory Catalog is intact; so it's the saving to the backend that is going wrong
        Database.saveDatabase(testCat, testUrl);

        // Now check the saved form:
        testCat = Database.loadDatabase(testUrl);
        expectedCatalogList = "ID #: 1; Title: Hello; Author: World; # Copies: 10\nID #: 2; Title: Test; Author: Book; # Copies: 2";
        actualCatalogList = testCat.getCatalog();
        try {
            assertEquals(expectedCatalogList, actualCatalogList, "Error in Database class when saving Catalog to Database. Expected: " + expectedCatalogList + " got " + actualCatalogList);
        } catch (AssertionError e) {
            errorMessages.add(e.getMessage());
        }
        // Remove the database from the disk
        Path filePath = Paths.get("sample.db");
        try {
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

package com.twag.book_management_app.model;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    /**
     * Create a new database or load an existing one, loading all
     * Books into memory via a main.Catalog object.
     * @param url The name of the database to created or loaded.
     * @return main.Catalog object containing all books loaded into memory.
     */
    public static Catalog loadDatabase(String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                String tableCreation = "CREATE TABLE IF NOT EXISTS catalog ("
                        + "id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                        + "title TEXT NOT NULL,"
                        + "authorLast TEXT NOT NULL,"
                        + "authorFirst TEXT NOT NULL,"
                        + "genre TEXT NOT NULL,"
                        + "copies INTEGER NOT NULL"
                        + ");";
                Statement sqlStatement = conn.createStatement();
                sqlStatement.execute(tableCreation);

                // TODO: check if table is empty; if so, we're done here
                // Otherwise, we need to load the data into the main.Catalog
                // Returns a
                ResultSet result = sqlStatement.executeQuery("SELECT * FROM catalog");

                // TODO: convert this entire block to a function so that it can properly handle the early return when
                // main.Catalog does not need to be loaded
                // Probably, the function should return a main.Catalog object -- an empty but initialized one -- if
                // there is no result from SELECT; otherwise return a loaded one.
                    // TODO: loading logic; already have query result, so only need to generate the catalog from it
                    // Parse the query, and load the data into ArrayList<main.Book>
                ArrayList<Book> books = new ArrayList<>();
                while(result.next()) {
                    int currentId = result.getInt("id");
                    String title = result.getString("title");
                    String authorLast = result.getString("authorLast");
                    String authorFirst = result.getString("authorFirst");
                    String genre = result.getString("genre");
                    int copies = result.getInt("copies");

                    System.out.println("id: " + currentId + "; title: " + title + "; author: " + authorLast + ", " + authorFirst + "; Genre: " + genre +"; copies: " + copies);

                    Book currentBook = new Book(currentId, title, authorLast, authorFirst, genre, copies);
                    books.add(new Book(currentId, title, authorLast, authorFirst, genre, copies));
                }
                return new Catalog(books, books.size());
                
            } else {
                throw new SQLException();
            }
        } catch (SQLException sqlException) {
            System.err.println("Error reading/creating " + url);
            sqlException.printStackTrace();
        }
        return null; // Should never get here
    }

    public static boolean saveDatabase(Catalog currentCatalog, String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                // Insert if not existent, replace otherwise; if there is an ID conflict, replace the existing data
                // with the new data. Realistically I should do everything possible to avoid such an ID conflict, but
                // if it should happen, it doesn't seem as though it should be the end of the world?
                final int AUTHOR_LAST = 0;
                final int AUTHOR_FIRST = 1;
                String upsertCommand = "MERGE INTO catalog (id, title, authorLast, authorFirst, genre, copies) KEY(id) VALUES(?, ?, ?, ?, ?, ?)";
                ArrayList<Book> currentBookList = currentCatalog.returnBookList();
                for (Book b : currentBookList) {
                    // If we got here, the table already exists.
                    // All we have to do is insert the data into it.
                    PreparedStatement preparedStatement = conn.prepareStatement(upsertCommand);
                    preparedStatement.setInt(1, b.getBookId());
                    preparedStatement.setString(2, b.getTitle());
                    String[] authorNames = b.getAuthorFull();
                    preparedStatement.setString(3, authorNames[AUTHOR_LAST]);
                    preparedStatement.setString(4, authorNames[AUTHOR_FIRST]);
                    preparedStatement.setString(5, b.getGenre());
                    preparedStatement.setInt(6, b.getNumCopies());

                    int rowsAffected = preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Error saving database " + url);
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    public static void createSampleDatabase(String databaseURL, Book testBook) {
        
    }
}

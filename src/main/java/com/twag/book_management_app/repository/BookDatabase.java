package com.twag.book_management_app.repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

import com.twag.book_management_app.model.Book;
@Repository
public class BookDatabase {
    final private String url = "jdbc:postgresql://192.168.0.1:5432/postgres";
    final private Properties props = new Properties();

    public BookDatabase() {
        props.setProperty("user", "icpostgresql");
        props.setProperty("password", "testpassword");

        try (Connection conn = DriverManager.getConnection(url, props)) {
            System.out.println(conn.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            System.out.println("Error connecting to the database " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Inserts the Book object into the database.
     * @param bookToAdd The Book object to be stored in the database.
     */
    public void insert(Book bookToAdd) {
        try (Connection conn = DriverManager.getConnection(url, props)) {
            Statement st = conn.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS all_books(" +
                "id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, image STRING, title TEXT, author_last TEXT, author_first TEXT, genre TEXT, num_copies INTEGER)"
            );
            
            PreparedStatement ps = conn.prepareStatement("INSERT INTO all_books(image, title, author_last, author_first, author_last, genre, num_copies) " +
                "VALUES(?, ?, ?, ?, ?, ?)"
            );

            ps.setString(1, bookToAdd.getImagePath());
            ps.setString(2, bookToAdd.getTitle());
            ps.setString(3, bookToAdd.getAuthorLast());
            ps.setString(4, bookToAdd.getAuthorFirst());
            ps.setString(5, bookToAdd.getGenre());
            ps.setInt(6, bookToAdd.getNumCopies());

        } catch (SQLException e) {
            System.err.println("Error inserting Book object into database:");
            e.printStackTrace();
        }
    }

    /**
     * Return a list of all books in the database, in the order they were
     * originally added.
     * @return ArrayList containing all books.
     */
    public ArrayList<Book> getAll() {
        ArrayList<Book> res = new ArrayList<Book>();
        try (Connection conn = DriverManager.getConnection(url, props)) {
            Statement st = conn.createStatement();
            st.execute("SELECT * FROM all_books");
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                String imagePath = rs.getString(1);
                String title = rs.getString(2);
                String authorLast = rs.getString(3);
                String authorFirst = rs.getString(4);
                String genre = rs.getString(5);
                int numCopies = rs.getInt(6);
                res.add(new Book(imagePath, title, authorLast, authorFirst, genre, numCopies));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving list of all Book objects from the database:");
            e.printStackTrace();
        }
        return res;
    }

	/**
	 * Delete Book object from Database
	 */
	public void delete(int id) {

	}

    /**
     * Check if a table with  
     * @param tableName String containing the name of the table being looked up.
     * @param conn Connection to the database passed to the function.
     * @return True if table is found with rs.next(), false otherwise.
     * @throws SQLException
     */
    public static boolean tableExists(final String tableName, Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        
        try (ResultSet rs = metaData.getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }

	public boolean existsById(final int id) {
		try (Connection conn = DriverManager.getConnection(url)) {
			"SELECT * FROM all_books WHERE "
			System.out.println("Book with ID " + id + " not found.");
		} catch (SQLException e) {
			System.err.println("Error checking if Book object exists:");
			e.printStackTrace();
		}

		return true;
	}
}


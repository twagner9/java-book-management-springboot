package com.twag.book_management_app.repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

import com.twag.book_management_app.model.Book;
@Repository
public class BookDatabase {
	/* TODO: many of the statements for error logging and such are using simple prints
	   but in reality this may need to switch to propagating messages up to the 
	   Electron GUI so that they are readable. */
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
    public boolean insert(Book bookToAdd) {
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
			st.close();
			ps.close();

        } catch (SQLException e) {
            System.err.println("Error inserting Book object into database:");
            e.printStackTrace();
			return false;
        }
		return true;
    }

	/**
	 * Delete Book object from Database
	 */
	public boolean delete(int id) {
		try (Connection conn = DriverManager.getConnection(url)) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("DELETE * FROM all_books WHERE id = " + id);
			if (!rs.next()) {
				System.out.println("Book with ID " + id + " not found.");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("Error checking if Book object exists:");
			e.printStackTrace();
		}

		return true;
	}

    /**
     * Return a list of all books in the database, in the order they were
     * originally added.
     * @return ArrayList containing all books.
     */
    public ArrayList<Book> getAll() {
        ArrayList<Book> res = new ArrayList<Book>();
        try (Connection conn = DriverManager.getConnection(url, props)) {
			ResultSet rs = connectAndExecuteQuery("SELECT * FROM all_books");
			res = processResultSetFromSelectAllBooks(rs);
			rs.close();
        } catch (SQLException e) {
            System.err.println("Error retrieving list of all Book objects from the database:");
            e.printStackTrace();
        }
        return res;
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

	/**
	 * Verify that a book exists in the database via its ID number.
	 * @param id Book ID number.
	 * @return True if the query ResultSet has data, false otherwise.
	 */
	public boolean existsById(final int id) {
		try (Connection conn = DriverManager.getConnection(url)) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM all_books WHERE id = " + id);
			if (!rs.next()) {
				System.out.println("Book with ID " + id + " not found.");
				rs.close();
				return false;
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Error checking if Book object exists:");
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Return all books in the database in ascending order.
	 * @param columnName The column by which the books should be sorted.
	 * @return ArrayList containing all Book objects.
	 */
	public List<Book> getAllBooksOrderAsc(String columnName) {
		ArrayList<Book> res = new ArrayList<Book>();
		try {
			String query = "SELECT * FROM all_books ORDER by " + columnName + " ASC";
			ResultSet rs = connectAndExecuteQuery(query);
			res = processResultSetFromSelectAllBooks(rs);
			rs.close();
		} catch (SQLException e) {
			System.err.println("Error returning books sorted in ascending order:");
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Return all books in the database in descending order.
	 * @param columnName Column by which the books should be sorted.
	 * @return ArrayList containing all Book objects.
	 */
	public List<Book> getAllBooksOrderDesc(String columnName) {
		ArrayList<Book> res = new ArrayList<Book>();
		try {
			String query = "SELECT * FROM all_books ORDER by " + columnName + " DESC";
			ResultSet rs = connectAndExecuteQuery(query);
			res = processResultSetFromSelectAllBooks(rs);
			rs.close();
		} catch (SQLException e) {
			System.err.println("Error returning books sorted in descending order:");
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Execute the passed SQL query on the database.
	 * @param query The query to be executed.
	 * @return ResultSet containing the stored data.
	 * @throws SQLException
	 */
	private ResultSet connectAndExecuteQuery(String query) throws SQLException{
		Connection conn = DriverManager.getConnection(url);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}

	/**
	 * Process the ResultSet when making a query that selects all books.
	 * @param rs The ResultSet object that will be processed.
	 * @return ArrayList of the Book objects.
	 * @throws SQLException
	 */
	private ArrayList<Book> processResultSetFromSelectAllBooks(ResultSet rs) throws SQLException{
		ArrayList<Book> res = new ArrayList<Book>();
		while (rs.next()) {
			res.add(new Book(rs.getString("title"),
							 rs.getString("author_last"), 
							 rs.getString("author_first"), 
						 	 rs.getString("genre"), 
							 rs.getInt("num_copies")
						)
					);
			}		
		return res;
	}
}


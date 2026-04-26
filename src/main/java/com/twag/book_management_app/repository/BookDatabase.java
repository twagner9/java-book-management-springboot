package com.twag.book_management_app.repository;

import java.sql.*;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.twag.book_management_app.model.Book;

@Component
public class BookDatabase {
	/*
	 * TODO: many of the statements for error logging and such are using simple
	 * prints
	 * but in reality this may need to switch to propagating messages up to the
	 * Electron GUI so that they are readable.
	 */
	final private String url = "jdbc:postgresql://192.168.0.1:5432/postgres";
	// final private Properties props = new Properties();

	private final JdbcTemplate jdbc;
    
    @NonNull
	private static final RowMapper<Book> rowMapper = (rs, rowNum) -> new Book(
			rs.getInt("id"),
			rs.getString("image"),
			rs.getString("title"),
			rs.getString("author_last"),
			rs.getString("author_first"),
			rs.getString("genre"),
			rs.getInt("num_copies"));

	public BookDatabase(@NonNull DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	/**
	 * Inserts the Book object into the database.
	 * 
	 * @param bookToAdd The Book object to be stored in the database.
	 */
	public Integer insertAndReturnId(Book bookToAdd) {
		return jdbc.queryForObject("""
				INSERT INTO all_books (image, title, author_last, author_first, genre, num_copies)
				VALUES (?, ?, ?, ?, ?, ?)
				RETURNING id
				""",
				Integer.class,
				bookToAdd.getImagePath(),
				bookToAdd.getTitle(),
				bookToAdd.getAuthorLast(),
				bookToAdd.getAuthorFirst(),
				bookToAdd.getGenre(),
				bookToAdd.getNumCopies());
	}

	/**
	 * Delete Book object from Database
	 */
	public int delete(int id) {
		// try (Connection conn = DriverManager.getConnection(url)) {
		// Statement st = conn.createStatement();
		// ResultSet rs = st.executeQuery("DELETE * FROM all_books WHERE id = " + id);
		// if (!rs.next()) {
		// System.out.println("Book with ID " + id + " not found.");
		// return false;
		// }
		// } catch (SQLException e) {
		// System.err.println("Error deleting book object if Book object exists:");
		// e.printStackTrace();
		// }

		// return true;
		return jdbc.update("DELETE FROM all_books WHERE id = ?", id);
	}

	/**
	 * Return a list of all books in the database, in the order they were
	 * originally added.
	 * 
	 * @return ArrayList containing all books.
	 */
	public List<Book> getAll() {
		return jdbc.query("SELECT * FROM all_books", rowMapper);
	}

	public Book getBookById(int idVal) {
		return jdbc.queryForObject("SELECT * FROM all_books WHERE id = ?", rowMapper, idVal);
	}

	/**
	 * Check if a table with
	 * 
	 * @param tableName String containing the name of the table being looked up.
	 * @param conn      Connection to the database passed to the function.
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
	 * 
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
	 * 
	 * @param columnName The column by which the books should be sorted.
	 * @return ArrayList containing all Book objects.
	 */
	public List<Book> getAllBooksOrderAsc(String columnName) {
		// ArrayList<Book> res = new ArrayList<Book>();
		// try {
		// String query = "SELECT * FROM all_books ORDER by " + columnName + " ASC";
		// ResultSet rs = connectAndExecuteQuery(query);
		// res = processResultSetFromSelectAllBooks(rs);
		// rs.close();
		// } catch (SQLException e) {
		// System.err.println("Error returning books sorted in ascending order:");
		// e.printStackTrace();
		// }
		// return res;
		String query = "SELECT * FROM all_books ORDER BY " + columnName + " ASC";
		return jdbc.query(query, rowMapper);
	}

	/**
	 * Return all books in the database in descending order.
	 * 
	 * @param columnName Column by which the books should be sorted.
	 * @return ArrayList containing all Book objects.
	 */
	public List<Book> getAllBooksOrderDesc(String columnName) {
		// ArrayList<Book> res = new ArrayList<Book>();
		// try {
		// String query = "SELECT * FROM all_books ORDER by " + columnName + " DESC";
		// ResultSet rs = connectAndExecuteQuery(query);
		// res = processResultSetFromSelectAllBooks(rs);
		// rs.close();
		// } catch (SQLException e) {
		// System.err.println("Error returning books sorted in descending order:");
		// e.printStackTrace();
		// }
		// return res;
		String query = "SELECT * FROM all_books ORDER by " + columnName + " DESC";

		return jdbc.query(query, rowMapper);
	}

	/**
	 * Update the database's stored value at the given ID
	 * 
	 * @param id
	 * @param columnName
	 * @param updatedText
	 * @return
	 */
	public int update(int id, String columnName, String updatedText) {
		return jdbc.update("UPDATE all_books SET ? = ? WHERE id = ?", columnName, id, updatedText);
	}

    	/**
	 * Update the database's stored value at the given ID
	 * 
	 * @param id
	 * @param updatedText
	 * @return
	 */
	public int updateTitle(int id, String updatedTitle) {
		return jdbc.update("UPDATE all_books SET title = ? WHERE id = ?", updatedTitle, id);
	}

    	/**
	 * Update the database's stored value at the given ID
	 * 
	 * @param id
	 * @param updatedText
	 * @return
	 */
	public int updateAuthorLast(int id, String updatedAuthorLast) {
		return jdbc.update("UPDATE all_books SET author_last = ? WHERE id = ?", updatedAuthorLast, id);
	}

    	/**
	 * Update the database's stored value at the given ID
	 * 
	 * @param id
	 * @param updatedText
	 * @return
	 */
	public int updateauthorFirst(int id, String updatedAuthorFirst) {
		return jdbc.update("UPDATE all_books SET author_first = ? WHERE id = ?", updatedAuthorFirst, id);
	}

	// /**
	// * Execute the passed SQL query on the database.
	// * @param query The query to be executed.
	// * @return ResultSet containing the stored data.
	// * @throws SQLException
	// */
	// private ResultSet connectAndExecuteQuery(String query) throws SQLException{
	// Connection conn = DriverManager.getConnection(url);
	// Statement st = conn.createStatement();
	// ResultSet rs = st.executeQuery(query);
	// return rs;
	// }

	// /**
	// * Process the ResultSet when making a query that selects all books.
	// * @param rs The ResultSet object that will be processed.
	// * @return ArrayList of the Book objects.
	// * @throws SQLException
	// */
	// private ArrayList<Book> processResultSetFromSelectAllBooks(ResultSet rs)
	// throws SQLException{
	// ArrayList<Book> res = new ArrayList<Book>();
	// while (rs.next()) {
	// res.add(new Book(rs.getString("title"),
	// rs.getString("author_last"),
	// rs.getString("author_first"),
	// rs.getString("genre"),
	// rs.getInt("num_copies")
	// )
	// );
	// }
	// return res;
	// }
}

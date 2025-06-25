package com.twag.book_management_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String title;
    private String author;
    private int numCopies;

    /**
     * Default constructor; initialize declared members to defaults.
     */
    public Book() {
        id = -1;
        title = "";
        author = "";
        numCopies = -1;
    }

    /**
     * Preferred constructor: initialize a new main.Book object that can
     * be added to the main.Catalog.
     * @param book_title Title of new main.Book.
     * @param book_author Author of new main.Book.
     * @param copies Number of new copies.
     */
    public Book(String book_title, String book_author, int copies) {
        id = -1;
        title = book_title;
        author = book_author;
        numCopies = copies;
    }

    public Book(int currentId, String title, String author, int copies) {
        id = currentId;
        this.title = title;
        this.author = author;
        numCopies = copies;
    }

    // GETTERS:

    /**
     * Return book ID number
     * @return main.Book ID number in database.
     */
    public int getBookId() {
        return id;
    }

    /**
     * Return book details in pre-specified format.
     * @return String containing values assigned to member variables of this main.Book.
     */
    public String getDetails() {
        return "ID #: " + id + "; " + "Title: " + title + "; " + "Author: " + author + "; " + "# Copies: " + numCopies;
    }

    /**
     * Return title of this main.Book.
     * @return String of the title of the main.Book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return author of this book.
     * @return String of the author of the main.Book.
     */
    public String getAuthor() { return author; }

    /**
     * Return number of copies of this main.Book.
     * @return Number of copies of this main.Book.
     */
    public int getNumCopies() {
        return numCopies;
    }

    /**
     * Set the ID number of this main.Book.
     * @param idNum new ID number
     */
    public void setId(int idNum) {
        id = idNum;
    }

    /**
     * Assign main.Book a title.
     * @param newTitle String of book's title.
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Assign main.Book an author.
     * @param authorName String of main.Book's author.
     */
    public void setAuthor(String authorName) {
        author = authorName;
    }

    /**
     * Set number of copies of the main.Book.
     * @param nCopies integer of the number of copies.
     */
    public void setNumCopies(int nCopies) {
        numCopies = nCopies;
    }

    /**
     * Increase number of book copies.
     * @param numToAdd Amount by which to increase copies.
     */
    public void increaseCount(int numToAdd) { numCopies += numToAdd; }


    /**
     * Remove number of book copies.
     * @param numToRemove Amount by which to decrease copies.
     */
    public void decreaseCount(int numToRemove) { numCopies -= numToRemove; }

    /**
     *
     * @param o main.Book object to be compared with current main.Book object.
     * @return true if same object, or if both main.Book objects and parameters match.
     */
    @Override
    public boolean equals(Object o) {
        // This is standard comparison of
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        Book that = (Book) o;
        return this.title.equals(that.title) && this.author.equals(that.author);
    }
}

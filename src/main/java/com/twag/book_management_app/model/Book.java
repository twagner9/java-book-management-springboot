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

    private String imagePath;
    private String title;
    private String authorLast;
    private String authorFirst;
    private String genre;
    private int numCopies;

    /**
     * Default constructor; initialize declared members to defaults.
     */
    public Book() {
        id = -1;
        imagePath = "";
        title = "";
        authorLast = "";
        authorFirst = "";
        genre = "";
        numCopies = -1;
    }

    /**
     * Preferred constructor: initialize a new main.Book object that can
     * be added to the main.Catalog.
     * 
     * @param book_title  Title of new main.Book.
     * @param authorLast  Author last name.
     * @param authorFirst Author first name.
     * @param genre       Book genre.
     * @param copies      Number of new copies.
     */
    public Book(String book_title, String authorLast, String authorFirst, String genre, int copies) {
        id = -1;
        title = book_title;
        this.authorLast = authorLast;
        this.authorFirst = authorFirst;
        this.genre = genre;
        numCopies = copies;
    }
    
    public Book(String imagePath, String title, String authorLast, String authorFirst, String genre, int copies) {
        id = -1;
        this.imagePath = imagePath;
        this.title = title;
        this.authorLast = authorLast;
        this.authorFirst = authorFirst;
        this.genre = genre;
        numCopies = copies;
    }

    public Book(int currentId, String imagePath, String title, String authorLast, String authorFirst, String genre, int copies) {
        id = currentId;
        this.imagePath = imagePath;
        this.title = title;
        this.authorLast = authorLast;
        this.authorFirst = authorFirst;
        this.genre = genre;
        numCopies = copies;
    }

    // GETTERS:

    /**
     * Return book ID number
     * 
     * @return main.Book ID number in database.
     */
    public int getId() {
        return id;
    }

    /**
     * Return book image path.
     * 
     * @return main.Book image path in database.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Return book details in pre-specified format.
     * 
     * @return String containing values assigned to member variables of this
     *         main.Book.
     */
    public String getDetails() {
        return "ID #: " + id + "; " + "Title: " + title + "; " + "Author: " + authorLast + ", " + authorFirst
                + "; Genre: " + genre + "; # Copies: " + numCopies + "; Image path: " + imagePath;
    }

    /**
     * Return title of this main.Book.
     * 
     * @return String of the title of the main.Book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return array containing author's last name in index 0, first name in index 1.
     * 
     * @return String of the author of the main.Book.
     */
    public String[] getAuthorFull() {
        String[] tmp = { authorLast, authorFirst };
        return tmp;
    }

    /**
     * Return author's last name only.
     * 
     * @return String of author's last name.
     */
    public String getAuthorLast() {
        return this.authorLast;
    }

    /**
     * Return author's first name only.
     * 
     * @return String of author's first name.
     */
    public String getAuthorFirst() {
        return this.authorFirst;
    }

    /**
     * Return the Book's genre.
     * 
     * @return String of the Book's genre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Return number of copies of this main.Book.
     * 
     * @return Number of copies of this main.Book.
     */
    public int getNumCopies() {
        return numCopies;
    }

    /**
     * Set the ID number of this main.Book.
     * 
     * @param idNum new ID number
     */
    public void setId(int idNum) {
        id = idNum;
    }

    /**
     * Assign main.Book a title.
     * 
     * @param newTitle String of book's title.
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setImagePath(String newImgPath) {
        imagePath = newImgPath;
    }

    /**
     * Assign main.Book an author.
     * 
     * @param authorLast  String of main.Book's author's last name.
     * @param authorFirst String of main.Book's author's first name.
     */
    public void setAuthor(String authorLast, String authorFirst) {
        this.authorLast = authorLast;
        this.authorFirst = authorFirst;
    }

    /**
     * Change the genre of a Book object
     * 
     * @param newGenre The newly selected genre.
     */
    public void setGenre(String newGenre) {
        this.genre = newGenre;
    }

    /**
     * Set number of copies of the main.Book.
     * 
     * @param nCopies integer of the number of copies.
     */
    public void setNumCopies(int nCopies) {
        numCopies = nCopies;
    }

    /**
     * Increase number of book copies.
     * 
     * @param numToAdd Amount by which to increase copies.
     */
    public void increaseCount(int numToAdd) {
        numCopies += numToAdd;
    }

    /**
     * Remove number of book copies.
     * 
     * @param numToRemove Amount by which to decrease copies.
     */
    public void decreaseCount(int numToRemove) {
        numCopies -= numToRemove;
    }

    /**
     *
     * @param o main.Book object to be compared with current main.Book object.
     * @return true if same object, or if both main.Book objects and parameters
     *         match.
     */
    @Override
    public boolean equals(Object o) {
        // This is standard comparison of
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        Book that = (Book) o;
        return this.title.equals(that.title) && this.authorLast.equals(that.authorLast)
                && this.authorFirst.equals(that.authorFirst);
    }
}

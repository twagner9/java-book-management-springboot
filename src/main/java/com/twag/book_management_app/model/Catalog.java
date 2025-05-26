package com.twag.book_management_app.model;

import java.util.ArrayList;
import java.util.List;


/* FIXME: All get and set classes in the catalog will need a rework
 * once SQL is implemented; the main.Catalog class as a whole may no longer
 * be needed since the data for book objects will be inserted directly
 * into the sqlite3 database.
 */

public class Catalog {
    private final ArrayList<Book> catalogList;
    private int numTitles;
    private int latestId;

    /**
     * Default constructor
     */
    public Catalog() {
        numTitles = 0;
        latestId = 1;
        catalogList = new ArrayList<>();
    }

    /**
     * Preferred constructor; load book list into catalog, set the total
     * number of titles, and set the latest ID number.
     * @param bookList The list of books being added to the database.
     * @param totalBookCount The total number of books in the catalog.
     */
    public Catalog(ArrayList<Book> bookList, int totalBookCount) {
        catalogList = bookList;
        numTitles = totalBookCount;
        latestId = catalogList.getLast().getBookId() + 1;
    }

    public Catalog(List<Book> bookList, int totalBookCount) {
        catalogList = new ArrayList<>(bookList);
        numTitles = totalBookCount;
        latestId = catalogList.getLast().getBookId() + 1;
    }

    /**
     * Search the catalog for a book with this ID number using binary search.
     * If found, return the main.Book object; otherwise, return null.
     * @param idNum The ID number being searched.
     * @return main.Book object with the corresponding ID number if found, null otherwise.
     */
    public Book getBookById(int idNum) {
        // This is a good spot for binary search
        int l = 0;
        int r = catalogList.size() - 1;
        int idIndex = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int currentBookId = catalogList.get(m).getBookId();
            if (currentBookId == idNum) {
                idIndex = currentBookId;
                break;
            }

            if (currentBookId < idNum) l = m + 1;
            else r = m - 1;
        }
        if (idIndex != -1)
            return catalogList.get(idIndex - 1);
        else {
            System.out.println("No main.Book with ID " + idNum + " found.");
            return null;
        }
    }

    /**
     * Search for a main.Book by its title. This could technically be like author,
     * @param title The title being searched.
     * @return main.Book if the title is found, null if not.
     */
    public Book getBookByTitle(String title) {
        if (numTitles < 1) {
            System.err.println("Cannot retrieve main.Book from empty main.Catalog!");
            return null;
        }

        for (Book b : catalogList) {
            if (b.getTitle().equals(title)) {
                return b;
            }
        }

        System.out.println("No books with title " + title + " found.");
        return null;
    }

    /**
     * Search for main.Book objects by author.
     * @param authorName The name of the author being searched.
     * @return ArrayList containing all main.Book objects by that author.
     */
    public ArrayList<Book> authorSearch(String authorName) {
        if (numTitles < 1) {
            System.err.println("Cannot search for authors in empty main.Catalog!");
        }

        ArrayList<Book> booksByAuthor = new ArrayList<>();
        for (Book b : catalogList) {
            if (b.getAuthor().equals(authorName))
                booksByAuthor.addLast(b);
        }

        // TODO: this may need to go wherever this function is called rather than here.
        if (booksByAuthor.isEmpty())
            System.out.println("No books by " + authorName + " found.");

        return booksByAuthor;
    }

    // FIXME: NEEDS SQL REWORK
    /**
     * Return string of the contents within the catalog.
     * @return Newline separated string that is a list of Book objects in the Catalog.
     */
    public String getCatalog() {
        if (catalogList.isEmpty()) {
            return "Cannot print empty catalog.";
        }

        String full_cat = "";
        final int catSize = catalogList.size();
        for (int b = 0; b < catSize; b++) {
            full_cat = full_cat.concat(catalogList.get(b).getDetails());
            if (!(b == catSize -1))
                full_cat = full_cat.concat("\n");
        }
        return full_cat;
    }

    /**
     * Return the number of unique Books in the main.Catalog.
     * @return Integer of number of unique titles.
     */
    public int getNumTitles() { return numTitles; }

    // TODO: when implementing SQL, this and title sort should be fairly easy.
    /**
     * Return a string containing all main.Book objects, but sorted
     * alphabetically by author.
     * @return String sorting main.Book objects by author.
     */
    public String authorSort() {
        // TODO
        return "";
    }

    /**
     * Return a string containing all main.Book objects, but sorted
     * alphabetically by title.
     * @return String sorting main.Book objects by title.
     */
    public String titleSort() {
        // TODO
        return "";
    }


    /**
     * Add a main.Book to the main.Catalog. If the main.Book already exists, simply
     * increase the number of copies of that book. Otherwise, add the
     * new book to the catalog.
     * @param bookToAdd The main.Book object to be added.
     * @throws CatalogException If book adding fails for any reason, throw this exception to provide details about why.
     */
    public void addBook(Book bookToAdd) throws CatalogException {
        boolean increasedNumCopies = false;
        int numAdd = bookToAdd.getNumCopies();
        int catSize = catalogList.size();
        // FIXME: This goes ONLY based on title; we need to go based on title and author
        for (Book book : catalogList) {
            if (bookToAdd.getTitle().equals(book.getTitle())) {
                int curNumCopies = book.getNumCopies();
                book.increaseCount(numAdd);
                increasedNumCopies = true;
                if (curNumCopies != book.getNumCopies() - numAdd) {
                    throw new CatalogException("Failed to properly update Books; expected numCopies == " +
                            (curNumCopies + numAdd) + "; got " + book.getNumCopies());
                }
                break;
            }
        }

        if (!increasedNumCopies) {
            bookToAdd.setId(latestId);
            catalogList.addLast(bookToAdd);
            latestId++;
            if ( ! (catalogList.size() == catSize + 1)) {
                throw new CatalogException("Failed to add book to catalog. Expected catalog size == " +
                        catSize + "; got " + catalogList.size());
            }
            numTitles++;
        }
    }

    /**
     * Remove a certain number of copies of books from the main.Catalog. This could be
     * a single book or many; if the count hits 0, the main.Book is removed from the
     * main.Catalog completely
     * @param bookId ID number of the main.Book having copies subtracted.
     * @param numToRemove The number of copies that should be removed.
     * @throws CatalogException If book removal fails for any reason, throw to provide details about why.
     */
    public void removeBook(int bookId, int numToRemove) throws CatalogException {
        int curCopies = catalogList.get(bookId - 1).getNumCopies();
        if ( curCopies < numToRemove) {
            throw new CatalogException("Cannot remove more copies than exist! " + curCopies +
                    "; trying to remove " + numToRemove);
        }
        else {
            catalogList.get(bookId - 1).decreaseCount(numToRemove);
            if (catalogList.get(bookId - 1).getNumCopies() == 0) catalogList.remove(bookId - 1);
        }
    }

    public ArrayList<Book> returnBookList() {
        return catalogList;
    }

    public static class CatalogException extends Exception {
        public CatalogException(String message) {
            super(message);
        }

        public CatalogException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

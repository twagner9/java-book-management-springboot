package com.twag.book_management_app.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("""
                
                
                Welcome to Bookshop Management System. With this program, you will be able to\
                
                efficiently manage your catalog of books, including tracking the number of\
                
                copies, the title, and the authors. Ability to add or remove books is also \
                
                available""");

        /* TODO: Instead of giving the user an option to enter a catalog name later, we're going to ask if they have
         * an existing database, or if they want to load from an existing database.
         */
        Scanner scn = new Scanner(System.in);
        String filename = Utils.getStringFromUser("Enter path to catalog: ", scn);
        String url = "jdbc:h2:file:./data/" + filename;
        Catalog catalog = Database.loadDatabase(url);

        // Ensure that the catalog can never be null
        try {
            Assert.assertNotNull(catalog, "Error: database opening " + url + " failed.");
        } catch (AssertionError e) {
            System.err.println(e.getMessage());
            System.err.println("Exiting...");
            return;
        }

        boolean programRunning = true;
//        boolean catalogIsSaved = false;
        final int minId = 1;
        String currentCommand;
        while (programRunning) {
            System.out.println("""
                    
                    Please enter one of the commands below:\
                    
                    1. List books: l
                    2. Add book: a
                    3. Remove book: r\
                    
                    4. Title search: t
                    5. Author search: u
                    6. ID search: i
                    7. Quit program: q
                    8. Open existing catalog: o
                    9. Save current catalog: w
                    """);
            currentCommand = scn.next().trim();
            char command = currentCommand.charAt(0);
            switch(command) {
                // Quit
                case 'q':
                // TODO: check if user wants to save before exiting
                    String userResponse = "";
                    while (!userResponse.equals("Y") && !userResponse.equals("N")) {
                        userResponse = Utils.getStringFromUser("Would you like to save the catalog before quitting (Y/N): ", scn).toUpperCase();
                    }
                    if (userResponse.equals("Y")) {
                        Database.saveDatabase(catalog, url);
                    } else {
                        System.out.println("Exiting without saving.");
                    }
                    programRunning = false;
                    scn.close();
                    break;
                // List
                case 'l':
                    System.out.println(catalog.getCatalog());
                    break;
                // Add book
                case 'a':
                    Book userBook = Utils.receiveBookInput(scn);
                    try {
                        catalog.addBook(userBook);
                        System.out.println("Successfully added book.");
                    } catch (Catalog.CatalogException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'r':
                    int idNum;
                    idNum = Utils.getIntFromUser("Enter the book's ID number: ", 1, catalog.getNumTitles(), scn);
                    int maxCopies = catalog.getBookById(idNum).getNumCopies();
                    int copiesToRemove = Utils.getIntFromUser("Enter number of copies (1 - " + maxCopies + "): ", 1, maxCopies, scn);
                    try {
                        catalog.removeBook(idNum, copiesToRemove);
                    } catch (Catalog.CatalogException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 't':
                    String sTitle = Utils.getStringFromUser("Enter title of book: ", scn);
                    try {
                        Book titleSearchBook = catalog.getBookByTitle(sTitle);
                        System.out.println(titleSearchBook.getDetails());
                    } catch (NullPointerException e) {
                        System.out.println("No book with title " + sTitle + " found");
                    }
                    break;
                case 'u':
                    String authLast = Utils.getStringFromUser("Enter the last name of the author: ", scn);
                    String authFirst = Utils.getStringFromUser("Enter the first name of the author: ", scn);
                    ArrayList<Book> authorResult = catalog.authorSearch(authLast, authFirst);
                    if (authorResult.isEmpty())
                        System.out.println("No titles from this author found.");
                    else
                        for (Book result : authorResult) {
                            System.out.println(result.getDetails());
                        }
                    break;
                case 'i':
                    int wantedId = Utils.getIntFromUser("Enter the ID number: ", minId, catalog.getNumTitles(), scn);
                    Book idSearchBook = catalog.getBookById(wantedId);
                    if (idSearchBook == null) {
                        System.out.println("No book with ID number " + wantedId + " found.");
                    } else {
                        System.out.println("main.Book found; details:\n" + idSearchBook.getDetails());
                    }
                    break;
                case 'o':
                    catalog = Utils.readCatalogFile();
                    break;
                case 'w':
                    boolean successfulWrite = Database.saveDatabase(catalog, url);
                    System.out.println(successfulWrite ? "Wrote catalog successfully." : "Failed to write catalog.");
                    break;
                default:
                    System.out.println("Enter only one of the valid commands listed.");
                    break;
            }
        }
        System.out.println("\nThank you for using Bookshop Management System!");
    }
}

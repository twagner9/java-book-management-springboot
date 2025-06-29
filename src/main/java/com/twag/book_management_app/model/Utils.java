package com.twag.book_management_app.model;

import java.io.*;
import java.util.Scanner;

public class Utils {

    /**
     * Allow a user to enter the parameters required for creating a
     * new main.Book object.
     * @return main.Book object containing the user's specified parameters.
     */
    public static Book receiveBookInput(Scanner scanner) {
        // FIXME: instead of passing the inputStream, pass a Scanner containing the inputStream
        String title = getStringFromUser("Enter a title: ", scanner);
        String authLast = getStringFromUser("Enter author last name: ", scanner);
        String authFirst = getStringFromUser("Enter author first name: ", scanner);
        String genre = getStringFromUser("Enter book genre: ", scanner);
        int numCopies = getIntFromUser("Enter number of copies: ", 1, 9999, scanner);
        scanner.close();
        return new Book(title, authLast, authFirst, genre, numCopies);
    }

    /**
     * Retrieve in an integer from user input between the upper and lower boundaries.
     * @param inputPrompt String specifying what integer input is expected.
     * @param lowerBound Smallest allowed value for the integer.
     * @param upperBound Greatest allowed value for the integer.
     * @param scanner Scanner object containing the input stream.
     * @return The user's valid integer within upper and lower bounds.
     */
    public static int getIntFromUser(String inputPrompt, int lowerBound, int upperBound, Scanner scanner) {
        int n = -1;
        while (n < lowerBound || n > upperBound) {
            System.out.print(inputPrompt);
            n = scanner.nextInt();
            if (n < lowerBound || n > upperBound)
                System.out.println("Bad input: enter a number greater than or equal to " + lowerBound +
                        " and less than and equal to " + upperBound);
            
        }
        return n;
    }

    /**
     * Return a string from user keyboard input.
     * @param inputPrompt The message specifying what the user should enter.
     * @return The user's input String.
     */
    public static String getStringFromUser(String inputPrompt, Scanner scanner) {
        String input = "";
        while (input.isBlank()) {
            System.out.println(inputPrompt);
            if (scanner.hasNextLine())
                input = scanner.nextLine();
        }
        return input;
    }

    // FIXME: this is earmarked for changing to be consistent with using SQLite db.
    /**
     * Write the catalog to a text file on the disk. Creates a new file if it does not
     * currently exist, otherwise clear the old and write to it.
     * @param curCatalog The current catalog that needs to be saved.
     * @return True on successful save, false otherwise.
     */
    public static boolean writeCatalogToFile(Catalog curCatalog, String filename) {
        if (! (curCatalog.getNumTitles() > 0)) {
            System.out.println("Cannot write an empty catalog.");
            return false;
        }

        boolean fileExisted;
        try {
            File catalogFile = new File(filename);
            // TODO: clear and write the contents of the catalog?
            fileExisted = !catalogFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Error opening file!");
            e.printStackTrace();
            return false;
        }

        // This is a try-with-resource, so it will only proceed if the file exists.
        try (FileWriter writer = new FileWriter(filename)) {

            // Clear the file if it exists already
            if (fileExisted)
                writer.write("");
            String catalogString = curCatalog.getCatalog();
            writer.write(catalogString);
        } catch (IOException e) {
            System.err.println("FileWriter Error!");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // FIXME: this is earmarked for changing to be consistent with using SQLite db.
    /**
     * Read in the file containing a previously made catalog.
     * @return Filled main.Catalog if successful, null otherwise.
     */
    public static Catalog readCatalogFile() {
        // NOTE: only making this to temporarily make this compile and run.
        Scanner scanner = new Scanner(System.in);
        String catalogFileName = getStringFromUser("Enter the name of the catalog: ", scanner);
        Catalog readCatalog = new Catalog();

        try (BufferedReader reader = new BufferedReader(new FileReader(catalogFileName))) {
            String line;
            while((line = reader.readLine()) != null) {
                String title = null, authorLast = null, authorFirst = null, genre = null;
                int numCopies = -1;
                boolean hasTitle = false, hasAuthor = false, hasGenre = false, hasCopies = false;
                String[] tokens = line.split(";");
                for (String token : tokens) {
                    String[] keyValue = token.split(":", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        switch (key) {
                            case "Title":
                                title = value;
                                hasTitle = true;
                                break;
                            case "Author Last":
                                authorLast = value;
                                hasAuthor = true;
                                break;
                            case "Author First":
                                authorFirst = value;
                                hasAuthor = true;
                                break;
                            case "Genre":
                                genre = value;
                                hasGenre = true;
                            case "# Copies":
                                try {
                                    numCopies = Integer.parseInt(value);
                                    hasCopies = true;
                                } catch (NumberFormatException numFormEx) {
                                    System.out.println("Invalid number format for # Copies: " + value);
                                    numFormEx.printStackTrace();
                                    return null;
                                }
                                break;
                        }
                    }
                }
                if (hasTitle && hasAuthor && hasCopies && hasGenre) {
                    Book tmpBook = new Book(title, authorLast, authorFirst, genre, numCopies);
                    try {
                        readCatalog.addBook(tmpBook);
                    } catch (Catalog.CatalogException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    throw new IOException("Invalid file format.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error finding the file!");
            e.printStackTrace();
            return null;
        }
        return readCatalog;
    }

    public static class InputException extends Exception {
        InputException(String message) {
            super(message);
        }
    }
}

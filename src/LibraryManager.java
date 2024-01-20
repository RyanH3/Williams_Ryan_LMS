/*
 * Ryan Williams
 * CEN 3024C-26663 Software Development I
 * 19 January 2024
 * LibraryManager.java
 * This class will present a menu for the user to navigate to add, remove, or view books in a library's collection.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;

public class LibraryManager {
    public static void main(String[] args){
        ArrayList<Book> library = new ArrayList<>();

        boolean quit = false;
        while(!quit) {
            int choice = menu();
            switch(choice) {
                case 1:
                    printLibrary(library);
                    break;
                case 2:
                    try {
                        library = addBook(library);
                        break;
                    }
                    catch (FileNotFoundException fnf) {
                        System.out.println("File not found.");
                        pause();
                        break;
                    }
                case 3:
                    library = removeBook(library);
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    quit = true;
                    break;
            }
        }
    }

    /*
     * Method Name: addBook
     * Purpose: Adds one or more book entries to the library collection from a text file
     * Parameters: ArrayList<Book>
     * Returns: ArrayList<Book>
     */
    static ArrayList<Book> addBook(ArrayList<Book> lib) throws FileNotFoundException {
        File newBookFile = new File("newBooks.txt");
        Scanner myScanner = new Scanner(newBookFile);

        int incompletes = 0;
        int invalids = 0;
        int duplicates = 0;
        int addedBooks = 0;
        boolean duplicateFlag;

        while (myScanner.hasNextLine()) {
            String newBookData = myScanner.nextLine();
            String[] newBookItems = newBookData.split(",");

            // skip and take note of incomplete entries
            if (newBookItems.length < 3) {
                incompletes++;
                continue;
            }
            // skip and take note of entries with IDs that are not numbers or negative
            try {
                Integer.parseInt(newBookItems[0]);
                if (Integer.parseInt(newBookItems[0]) < 0) {
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException nfe) {
                invalids++;
                continue;
            }
            // skip and take note of entries with duplicate IDs
            duplicateFlag = false;
            for (Book book : lib) {
                if (Integer.parseInt(newBookItems[0]) == book.getId()) {
                    duplicateFlag = true;
                    duplicates++;
                }
            }
            if (duplicateFlag) {
                continue;
            }

            int newBookId = Integer.parseInt(newBookItems[0]);
            String newBookTitle = newBookItems[1];
            String newBookAuthor = newBookItems[2];
            Book newBook = new Book(newBookId, newBookTitle, newBookAuthor);
            lib.add(newBook);
            addedBooks++;
        }

        // make "entry" singular or plural based on the amount
        System.out.println("Process completed. " +
                addedBooks + (addedBooks == 1 ? " book added. " : " books added. ") +
                incompletes + (incompletes == 1 ? " incomplete entry, " : " incomplete entries, ") +
                invalids + (invalids == 1 ? " invalid ID, and " : " invalid IDs, and ") +
                duplicates + (duplicates == 1 ? " duplicate entry found." : " duplicate entries found."));
        pause();
        return lib;
    }

    /*
     * Method Name: menu
     * Purpose: Prints a menu to guide the user through the system
     * Parameters: none
     * Returns: int
     */
    static int menu() {
        System.out.println("""
                ========================
                ==     Main  Menu     ==
                ========================
                Welcome to the Library Manager!
                Press the number of the option that you want to select, then press ENTER.
                1. Show
                2. Add
                3. Remove
                4. Quit""");

        // makes sure the user only inputted an integer from 1-4
        boolean flag = true;
        int choice = 0;
        while(flag) {
            try {
                Scanner myScanner = new Scanner(System.in);
                choice = myScanner.nextInt();
                if (choice < 1 || choice > 4) {
                    throw new InputMismatchException();
                }
                flag = false;
            }
            catch (InputMismatchException ime) {
                System.out.println("Please type a number from 1 to 4.");
            }
        }
        return choice;
    }

    /*
     * Method Name: pause
     * Purpose: Pauses the system until the user presses ENTER
     * Parameters: none
     * Returns: nothing
     */
    public static void pause() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("\nPress ENTER to continue...");
        myScanner.nextLine();
    }

    /*
     * Method Name: printLibrary
     * Purpose: Prints all Books currently in the library
     * Parameters: ArrayList<Book>
     * Returns: nothing
     */
    static void printLibrary(ArrayList<Book> lib){
        if (lib.isEmpty()) {
            System.out.println("There are no books in the library.");
        }

        // sorts the list before displaying it
        lib.sort(Comparator.comparing(Book::getId));

        for (Book book : lib) {
            book.print();
        }
        pause();
    }

    /*
     * Method Name: removeBook
     * Purpose: Removes a book from the library collection based on its ID
     * Parameters: ArrayList<Book>
     * Returns: ArrayList<Book>
     */
    static ArrayList<Book> removeBook(ArrayList<Book> lib) {
        if(lib.isEmpty()) {
            System.out.println("There are no books in the library.");
            pause();
            return lib;
        }

        boolean flag = true;
        while(flag) {
            System.out.println("Type the ID of the book you want to remove. Type -1 to back out.");
            try {
                Scanner myScanner = new Scanner(System.in);
                int bookId = myScanner.nextInt();
                lib.removeIf(book -> (book.getId() == bookId));
                flag = false;
            }
            catch (InputMismatchException ime) {
                System.out.println("Please type a whole number");
                pause();
            }
        }
        return lib;
    }
}

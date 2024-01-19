/*
 * Ryan Williams
 * CEN 3024C-26663 Software Development I
 * 19 January 2024
 * LibraryManager.java
 * This class will present a menu for the user to navigate to add, remove, or view books in a library's collection.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

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
                        System.out.println("File not found");
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

        int duplicate = 0;
        boolean duplicateFlag;

        while (myScanner.hasNextLine()) {
            String newBookData = myScanner.nextLine();

            //break newBook into three variables
            String[] newBookDataList = newBookData.split(",");
            int newBookId = Integer.parseInt(newBookDataList[0]);
            String newBookTitle = newBookDataList[1];
            String newBookAuthor = newBookDataList[2];

            // make sure no books with duplicate IDs are added to the library and count duplicates
            duplicateFlag = false;
            for (Book book : lib) {
                if (newBookId == book.getId()) {
                    duplicateFlag = true;
                    duplicate++;
                }
            }

            if (!duplicateFlag) {
                Book newBook = new Book(newBookId, newBookTitle, newBookAuthor);
                lib.add(newBook);
            }
        }

        System.out.println("Process completed. " + duplicate + " duplicates found.");
        return lib;
    }

    /*
     * Method Name: menu
     * Purpose: Prints a menu to guide the user through the system
     * Parameters: none
     * Returns: int
     */
    static int menu() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("1. Show\n" +
                "2. Add\n" +
                "3. Remove\n" +
                "4. Quit\n\n" +
                "Press the number of the option that you want to select then press ENTER.");
        return myScanner.nextInt();
    }

    /*
     * Method Name: printLibrary
     * Purpose: Prints all Books currently in the library
     * Parameters: ArrayList<Book>
     * Returns: nothing
     */
    static void printLibrary(ArrayList<Book> lib){
        for (Book book : lib) {
            book.print();
        }
    }

    /*
     * Method Name: removeBook
     * Purpose: Removes a book from the library collection based on its ID
     * Parameters: ArrayList<Book>
     * Returns: ArrayList<Book>
     */
    static ArrayList<Book> removeBook(ArrayList<Book> lib) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Type the ID of the book you want to remove.");
        int bookId = myScanner.nextInt();

        lib.removeIf(book -> (book.getId() == bookId));
        return lib;
    }
}

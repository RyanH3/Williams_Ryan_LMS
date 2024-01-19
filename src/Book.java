/*
 * Ryan Williams
 * CEN 3024C-26663 Software Development I
 * 19 January 2024
 * Book.java
 * This class will be used to create Books to store in the library's collection.
 */
public class Book {
    private int id;
    private String title;
    private String author;

    public Book() {
        this.id = 999;
        this.title = "Book";
        this.author = "John Smith";
    }

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    /*
     * Method Name: print
     * Purpose: Prints information about the Book
     * Parameters: none
     * Returns: nothing
     */
    public void print() {
        System.out.println("Book " + this.id + ": " + this.title + ", by " + this.author);
    }
}

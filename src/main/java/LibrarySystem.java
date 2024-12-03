import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/LibraryDB";
        String username = ""; // Update with MySQL username
        String password = ""; // Update with password

        DatabaseConnection dbConnection = new DatabaseConnection(jdbcUrl, username, password);
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
            connection = dbConnection.connect();
            BookDAO bookDAO = new BookDAO(connection);
            int choice;

            do {
                System.out.println("\nLibrary System Menu:");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Update Book");
                System.out.println("4. Delete Book");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        // Add Book
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter total copies: ");
                        int totalCopies = scanner.nextInt();
                        System.out.print("Enter available copies: ");
                        int availableCopies = scanner.nextInt();
                        bookDAO.insertBook(new Book(0, title, author, totalCopies, availableCopies));
                        break;
                    case 2:
                        // View Books
                        List<Book> books = bookDAO.fetchBooks();
                        books.forEach(book -> 
                            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + 
                                               ", Author: " + book.getAuthor() + 
                                               ", Total Copies: " + book.getTotalCopies() + 
                                               ", Available Copies: " + book.getAvailableCopies()));
                        break;
                    case 3:
                        // Update Book
                        System.out.print("Enter book ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new title: ");
                        String newTitle = scanner.nextLine();
                        System.out.print("Enter new author: ");
                        String newAuthor = scanner.nextLine();
                        System.out.print("Enter new total copies: ");
                        int newTotalCopies = scanner.nextInt();
                        System.out.print("Enter new available copies: ");
                        int newAvailableCopies = scanner.nextInt();
                        bookDAO.updateBook(new Book(updateId, newTitle, newAuthor, newTotalCopies, newAvailableCopies));
                        break;
                    case 4:
                        // Delete Book
                        System.out.print("Enter book ID to delete: ");
                        int deleteId = scanner.nextInt();
                        bookDAO.deleteBook(deleteId);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);

        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        } finally {
            dbConnection.disconnect();
            scanner.close();
        }
    }
}

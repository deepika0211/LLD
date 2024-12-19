import java.util.List;

// Interface for Library Management
public interface LibraryManager {
    boolean issueBook(Book book, Reader reader);

    boolean returnBook(Reader reader);

    boolean reserveBook(Book book, Reader reader);

    List<Book> searchBooks(String query);

    void displayOverdueBooks();
}

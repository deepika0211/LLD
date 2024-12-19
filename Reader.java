import java.util.ArrayList;
import java.util.List;

public class Reader extends Person {
    private Book currentlyIssuedBook;
    private List<Book> reservedBooks;

    public Reader(String id, String name) {
        super(id, name);
        this.reservedBooks = new ArrayList<>();
    }

    public Book getCurrentlyIssuedBook() {
        return currentlyIssuedBook;
    }

    public void setCurrentlyIssuedBook(Book book) {
        this.currentlyIssuedBook = book;
    }

    public List<Book> getReservedBooks() {
        return reservedBooks;
    }

    public void reserveBook(Book book) {
        reservedBooks.add(book);
    }
}

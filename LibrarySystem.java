import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

// Library System Implementation
public class LibrarySystem implements LibraryManager {
    private final Map<String, Book> booksCatalog = new HashMap<>();
    private final AtomicReference<Reader> activeReader = new AtomicReference<>(null);
    private final Map<Reader, Date> issueDates = new HashMap<>();

    public LibrarySystem() {
        // Initialize books in the catalog
        booksCatalog.put("B001", BookFactory.createBook("Comic", "B001", "The Adventures of Tom", "Mark Twain"));
        booksCatalog.put("B002", BookFactory.createBook("Fiction", "B002", "1984", "George Orwell"));
        booksCatalog.put("B003", BookFactory.createBook("NonFiction", "B003", "Sapiens", "Yuval Noah Harari"));
    }

    @Override
    public synchronized boolean issueBook(Book book, Reader reader) {
        if (!setActiveReader(reader)) {
            System.out.println("Another reader is already active. Please wait.");
            return false;
        }
        if (reader.getCurrentlyIssuedBook() != null) {
            System.out.println(reader.getName() + " already has an issued book.");
            return false;
        }
        if (book.getStatus() != BookStatus.AVAILABLE) {
            System.out.println("Book is not available for issuing.");
            return false;
        }
        book.setStatus(BookStatus.ISSUED);
        reader.setCurrentlyIssuedBook(book);
        issueDates.put(reader, new Date());
        System.out.println("Book '" + book.getTitle() + "' issued to " + reader.getName());
        return true;
    }

    @Override
    public synchronized boolean returnBook(Reader reader) {
        if (!isActiveReader(reader)) {
            System.out.println("Only the active reader can return a book.");
            return false;
        }
        Book book = reader.getCurrentlyIssuedBook();
        if (book == null) {
            System.out.println(reader.getName() + " has no issued book to return.");
            return false;
        }
        book.setStatus(BookStatus.AVAILABLE);
        reader.setCurrentlyIssuedBook(null);
        issueDates.remove(reader);
        clearActiveReader();
        System.out.println("Book '" + book.getTitle() + "' returned by " + reader.getName());
        return true;
    }

    @Override
    public boolean reserveBook(Book book, Reader reader) {
        if (book.getStatus() != BookStatus.AVAILABLE) {
            System.out.println("Book is not available for reservation.");
            return false;
        }
        book.setStatus(BookStatus.RESERVED);
        reader.reserveBook(book);
        System.out.println("Book '" + book.getTitle() + "' reserved by " + reader.getName());
        return true;
    }

    @Override
    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : booksCatalog.values()) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase())
                    || book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public void displayOverdueBooks() {
        Date now = new Date();
        for (Map.Entry<Reader, Date> entry : issueDates.entrySet()) {
            long diff = now.getTime() - entry.getValue().getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days > 7) {
                System.out.println(entry.getKey().getName() + " has an overdue book: '"
                        + entry.getKey().getCurrentlyIssuedBook().getTitle() + "' issued " + days + " days ago.");
            }
        }
    }

    private boolean setActiveReader(Reader reader) {
        return activeReader.compareAndSet(null, reader);
    }

    private void clearActiveReader() {
        activeReader.set(null);
    }

    private boolean isActiveReader(Reader reader) {
        return activeReader.get() == reader;
    }
}

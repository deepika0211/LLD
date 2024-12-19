// Main Class
public class Main {
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();

        Reader alice = ReaderFactory.createReader("R001", "Alice");
        Reader bob = ReaderFactory.createReader("R002", "Bob");

        Book book1 = library.searchBooks("Tom").get(0);

        library.issueBook(book1, alice);
        library.reserveBook(book1, bob);
        library.returnBook(alice);
        library.issueBook(book1, bob);
        library.displayOverdueBooks();
    }
}

// Factory for creating Books
public class BookFactory {
    public static Book createBook(String category, String id, String title, String author) {
        switch (category) {
            case "Fiction":
                return new FictionBook(id, title, author);
            case "Comic":
                return new ComicBook(id, title, author);
            default:
                return new NonFictionBook(id, title, author);
        }
    }
}

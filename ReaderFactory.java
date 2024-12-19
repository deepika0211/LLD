// Factory for creating Readers
public class ReaderFactory {
    public static Reader createReader(String id, String name) {
        return new Reader(id, name);
    }
}

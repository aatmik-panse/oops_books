package bookrepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DatasetReader {

    // internal storage for parsed books
    private final List<Book> books = new ArrayList<>();

    /**
     * Read the CSV dataset and return the parsed books.
     * This method throws IOException to allow the caller to decide how to handle errors.
     */
    public List<Book> readDataset(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        try (Stream<String> lines = Files.lines(path)) {
            // If the CSV has a header row, skip it. If not, remove skip(1).
            lines.filter(l -> l != null && !l.trim().isEmpty())
                 .skip(1)
                 .forEach(line -> {
                     String[] values = line.split(",");
                     try {
                         String title = values.length > 0 ? values[0] : "";
                         String author = values.length > 1 ? values[1] : "";
                         float userRating = (values.length > 2 && !values[2].isEmpty()) ? Float.parseFloat(values[2]) : 0f;
                         String reviews = values.length > 3 ? values[3] : "";
                         String genre = values.length > 4 ? values[4] : "";
                         int year = (values.length > 5 && !values[5].isEmpty()) ? Integer.parseInt(values[5]) : 0;
                         float price = (values.length > 6 && !values[6].isEmpty()) ? Float.parseFloat(values[6]) : 0f;

                         Book book = new Book(title, author, userRating, reviews, genre, year, price);
                         addBook(book);
                     } catch (NumberFormatException ex) {
                         System.err.println("Skipping line with malformed number: " + line);
                     }
                 });
        }

        return new ArrayList<>(books);
    }

    public void addBook(Book book) {
        if (book == null) return;
        books.add(book);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

}

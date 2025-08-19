package bookrepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class DatasetReader {

    private final List<Book> books = new ArrayList<>();

    public List<Book> readDataset(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(l -> l != null && !l.trim().isEmpty())
                 .skip(1) // skip header 
                 .forEach(line -> {
                     List<String> values = parseCsvLine(line);
                     try {
                         String title = get(values,0);
                         String author = get(values,1);
                         float userRating = parseFloat(get(values,2));
                         int reviews = parseInt(get(values,3));
                         float price = parseFloat(get(values,4));
                         int year = parseInt(get(values,5));
                         String genre = get(values,6);

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


    public long countBooksByAuthor(String author) {
        if (author == null) return 0;
        return books.stream().filter(b -> author.equalsIgnoreCase(b.getAuthor())).count();
    }

    public Set<String> allAuthors() {
        Set<String> set = new HashSet<>();
        for (Book b : books) {
            if (b.getAuthor() != null && !b.getAuthor().isBlank()) {
                set.add(b.getAuthor());
            }
        }
        return set;
    }

    public List<String> bookTitlesByAuthor(String author) {
        List<String> titles = new ArrayList<>();
        if (author == null) return titles;
        for (Book b : books) {
            if (author.equalsIgnoreCase(b.getAuthor())) {
                titles.add(b.getTitle());
            }
        }
        return titles;
    }

    public List<Book> booksByExactRating(float rating) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (Float.compare(b.getUserRating(), rating) == 0) {
                result.add(b);
            }
        }
        return result;
    }

    public List<String> bookTitlesAndPricesByAuthor(String author) {
        List<String> result = new ArrayList<>();
        if (author == null) return result;
        for (Book b : books) {
            if (author.equalsIgnoreCase(b.getAuthor())) {
                result.add(String.format("%s ($%.2f)", b.getTitle(), b.getPrice()));
            }
        }
        return result;
    }


    private static String get(List<String> list, int idx) { return idx < list.size() ? list.get(idx).trim() : ""; }
    private static float parseFloat(String s){ if (s==null||s.isEmpty()) return 0f; return Float.parseFloat(s); }
    private static int parseInt(String s){ if (s==null||s.isEmpty()) return 0; return Integer.parseInt(s); }

    private static List<String> parseCsvLine(String line) {
        List<String> oki = new ArrayList<>();
        if (line == null) return oki;
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                oki.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        oki.add(sb.toString());
        return oki;
    }

}

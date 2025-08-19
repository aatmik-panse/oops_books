package bookrepo;

import java.io.*;
import java.util.*;

public class driver {
    public static void main(String[] args) {
    String defaultPath = "bestsellers with categories.csv";
        String path = (args != null && args.length > 0) ? args[0] : defaultPath;

        DatasetReader datasetReader = new DatasetReader();
        try {
            List<Book> books = datasetReader.readDataset(path);
            if (books.isEmpty()) {
                System.out.println("No books loaded from: " + path);
                return;
            }

            Scanner sc = new Scanner(System.in);
            boolean running = true;
            while (running) {
                System.out.println("Choose an option:");
                System.out.println("1. Total number of books by an author");
                System.out.println("2. List all unique authors");
                System.out.println("3. List all book titles by an author");
                System.out.println("4. List all books with an exact user rating");
                System.out.println("5. List titles and prices of books by an author");
                System.out.println("6. Print ALL books (debug)");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter author name: ");
                        String author1 = sc.nextLine();
                        long count = datasetReader.countBooksByAuthor(author1);
                        System.out.println("Total books by '" + author1 + "': " + count);
                        break;
                    case "2":
                        System.out.println("Authors:");
                        datasetReader.allAuthors().stream().sorted().forEach(System.out::println);
                        break;
                    case "3":
                        System.out.print("Enter author name: ");
                        String author2 = sc.nextLine();
                        List<String> titles = datasetReader.bookTitlesByAuthor(author2);
                        if (titles.isEmpty()) System.out.println("No titles found.");
                        else titles.forEach(System.out::println);
                        break;
                    case "4":
                        System.out.print("Enter exact rating (e.g., 4.7): ");
                        try {
                            float r = Float.parseFloat(sc.nextLine());
                            List<Book> rated = datasetReader.booksByExactRating(r);
                            if (rated.isEmpty()) System.out.println("No books with rating " + r);
                            else rated.forEach(b -> System.out.println(b.getTitle() + " by " + b.getAuthor() + " (" + b.getUserRating() + ")"));
                        } catch (NumberFormatException ex) {
                            System.out.println("Invalid rating input.");
                        }
                        break;
                    case "5":
                        System.out.print("Enter author name: ");
                        String author3 = sc.nextLine();
                        List<String> priced = datasetReader.bookTitlesAndPricesByAuthor(author3);
                        if (priced.isEmpty()) System.out.println("No books found.");
                        else priced.forEach(System.out::println);
                        break;
                    case "6":
                        for (Book b : books) {
                            b.printDetails();
                            System.out.println("----------------------------------------");
                        }
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to read dataset:" + e.getMessage());
            e.printStackTrace();
        }
    }
}

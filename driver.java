package bookrepo;

import java.io.*;
import java.util.*;

public class driver {
    public static void main(String[] args) {
        String defaultPath = "bookrepo/bestsellers with categories.csv";
        String path = (args != null && args.length > 0) ? args[0] : defaultPath;

        DatasetReader datasetReader = new DatasetReader();
        try {
            List<Book> books = datasetReader.readDataset(path);
            if (books.isEmpty()) {
                System.out.println("No books loaded from: " + path);
                return;
            }

            for (Book b : books) {
                b.printDetails();
                System.out.println("----------------------------------------");
            }

        } catch (IOException e) {
            System.err.println("Failed to read dataset:" + e.getMessage());
            e.printStackTrace();
        }
    }
}

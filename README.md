# oops_books

Simple Java console app that explores the Kaggle dataset "Amazon Top 50 Bestselling Books (2009–2019)" (550 rows). You load the CSV and run quick queries from a menu.

---

## 1. What it does

Reads `bestsellers with categories.csv` and lets you:

1. Count how many bestseller entries an author has.
2. See all unique authors.
3. List all titles by a given author.
4. Find every book with an exact user rating (e.g. 4.7).
5. Show titles + prices for an author.
6. Dump all books (debug).
7. Exit.

---

## 2. Files

| File                              | Purpose                                                                          |
| --------------------------------- | -------------------------------------------------------------------------------- |
| `bestsellers with categories.csv` | Source dataset (headers: Name, Author, User Rating, Reviews, Price, Year, Genre) |
| `Book.java`                       | Book model (title, author, rating, reviews count, genre, year, price).           |
| `DatasetReader.java`              | Loads CSV + query helper methods.                                                |
| `driver.java`                     | Main class with interactive menu.                                                |

---

## 3. Quick start

From inside the `bookrepo` directory:

```bash
javac *.java
java -cp .. bookrepo.driver
```

Pass a custom CSV path if needed:

```bash
java -cp .. bookrepo.driver /path/to/other.csv
```

---

## 4. Example

```
1. Total number of books by an author
2. List all unique authors
3. List all book titles by an author
4. List all books with an exact user rating
5. List titles and prices of books by an author
6. Print ALL books (debug)
0. Exit
Enter choice: 1
Enter author name: Michelle Obama
Total books by 'Michelle Obama': 2
```

---

## 5. How CSV parsing works

`parseCsvLine` walks the line character by character and keeps text inside quotes together so book titles containing commas don’t break (e.g. "5,000 Awesome Facts..."). It’s minimal (doesn’t unescape doubled quotes).

---

## 6. Use of Java Streams

`DatasetReader.readDataset` uses `Files.lines(path)` which returns a `Stream<String>` of lines. We then chain:

```java
Files.lines(path)
	.filter(l -> l != null && !l.trim().isEmpty()) // remove blanks
	.skip(1)                                       // header
	.forEach(line -> { /* parse & build Book */ });
```

This keeps memory low (lazy line reading), auto-closes the file via try‑with‑resources, and provides a clear pipeline instead of a manual while loop.

---

package bookrepo;

public class Book {
    public String title;
    public String author;
    public float userRating;
    public int reviews; 
    public String genre;
    public int year;
    public float price;

    public Book(String title, String author, float userRating, int reviews, String genre, int year, float price) {
        this.title = title;
        this.author = author;
        this.userRating = userRating;
        this.reviews = reviews;
        this.genre = genre;
        this.year = year;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    

    // Instance method prints details for this book
    public void printDetails() {
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("User Rating: " + getUserRating());
        System.out.println("Reviews: " + getReviews());
        System.out.println("Genre: " + getGenre());
        System.out.println("Year: " + getYear());
        System.out.println("Price: $" + getPrice());
    }

    
}
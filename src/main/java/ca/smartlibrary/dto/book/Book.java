package ca.smartlibrary.dto.book;

import ca.smartlibrary.dto.Review;
import ca.smartlibrary.dto.library.Library;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Data
public class Book {
    private String id;
    private String isbn;
    private String title;
    private String synopsis;
    private String pictureUrl;

    private String author;
    private String narrator;
    private String publisher;
    private Instant publishedDate;

    private int pages;
    private Duration averageReadingTime;
    private BookFormat format;
    private Library library;
    private List<Review> reviews;
    private List<String> tags;


    public enum BookCategory {
        kids,
        teen,
        adult,

        graphic,
        mystery,
        romance,
        fiction,
        cookbook,
        education,
    }
}

package ca.smartlibrary.dto.book;

import ca.smartlibrary.dto.library.Library;
import lombok.Data;

import java.time.Instant;

@Data
public class Book {
    private String id;
    private String isbn;
    private String title;
    private String synopsis;

    private String author;
    private String narrator;
    private String publisher;
    private Instant publishedDate;

    private BookFormat format;
    private Library library;
}

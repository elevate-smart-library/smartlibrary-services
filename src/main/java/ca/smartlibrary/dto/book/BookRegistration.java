package ca.smartlibrary.dto.book;

import ca.smartlibrary.dto.library.Library;
import lombok.Data;

@Data
public class BookRegistration {

    private String isbn;
    private Library library;
}

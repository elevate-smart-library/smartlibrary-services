package ca.smartlibrary.service.book;

import ca.smartlibrary.controller.BasicRequestParamFilter;
import ca.smartlibrary.dto.book.Book;
import ca.smartlibrary.dto.book.Book.BookCategory;
import ca.smartlibrary.dto.book.BookFormat;
import ca.smartlibrary.dto.book.BookRegistration;
import ca.smartlibrary.dto.library.Library;
import ca.smartlibrary.service.library.LibraryService;
import ca.smartlibrary.utils.MockProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@Service
@MockProfile
public class MockBookService implements BookService {

    private static final List<BookCategory> BOOK_CATEGORIES = Arrays.asList(BookCategory.values());
    private final ObjectMapper mapper;
    private final List<Library> libraries;

    @Autowired
    public MockBookService(ObjectMapper mapper, LibraryService libraryService) {
        this.mapper = mapper;
        this.libraries = libraryService.findAll(null);
    }

    @Override
    public List<Book> findAll(BasicRequestParamFilter filter) {
        ClassPathResource data = new ClassPathResource("mock/books.json");
        try {
            return Arrays.stream(mapper.readValue(data.getInputStream(), Book[].class))
                    .peek(book -> {
                        // Library
                        if (BookFormat.PHYSICAL.equals(book.getFormat())) {
                            book.setLibrary(libraries.get(nextInt(0, libraries.size())));
                        }

                        // Tags
                        book.setTags(IntStream.of(0, nextInt(1, 4))
                                .mapToObj(i -> BOOK_CATEGORIES.get(nextInt(0, BOOK_CATEGORIES.size())).name())
                                .collect(Collectors.toList()));

                        // Reviews
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Book checkIn(String id) {
        return null;
    }

    @Override
    public Book checkout(String id) {
        return null;
    }

    @Override
    public Book register(BookRegistration bookRegistration) {
        return null;
    }
}

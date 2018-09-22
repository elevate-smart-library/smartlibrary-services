package ca.smartlibrary.controller;

import ca.smartlibrary.dto.book.Book;
import ca.smartlibrary.dto.book.BookRegistration;
import ca.smartlibrary.security.ScopeAuthority;
import ca.smartlibrary.service.book.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured({ScopeAuthority.LIBRARY_VALUE})
@RequestMapping("/api/v1/books")
@Api(tags = {"Book"}, description = "Book search")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAll(BasicRequestParamFilter filter) {
        return bookService.findAll(filter);
    }

    @PostMapping
    public Book register(@RequestBody BookRegistration bookRegistration) {
        return bookService.register(bookRegistration);
    }

    @PostMapping("/{id}")
    public Book checkIn(@PathVariable String id) {
        return bookService.checkIn(id);
    }

    @PutMapping("/{id}")
    public Book checkout(@PathVariable String id) {
        return bookService.checkout(id);
    }

}

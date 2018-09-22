package ca.smartlibrary.service.book;


import ca.smartlibrary.controller.BasicRequestParamFilter;
import ca.smartlibrary.dto.book.Book;
import ca.smartlibrary.dto.book.BookRegistration;

import java.util.List;

public interface BookService {

    List<Book> findAll(BasicRequestParamFilter filter);

    Book checkIn(String id);

    Book checkout(String id);

    Book register(BookRegistration bookRegistration);
}

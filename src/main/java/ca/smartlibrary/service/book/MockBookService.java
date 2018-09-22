package ca.smartlibrary.service.book;

import ca.smartlibrary.controller.BasicRequestParamFilter;
import ca.smartlibrary.dto.book.Book;
import ca.smartlibrary.utils.MockProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@MockProfile
public class MockBookService implements BookService {

    @Override
    public List<Book> findAll(BasicRequestParamFilter filter) {
        return null;
    }
}

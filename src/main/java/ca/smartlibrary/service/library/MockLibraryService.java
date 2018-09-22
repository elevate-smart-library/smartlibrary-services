package ca.smartlibrary.service.library;

import ca.smartlibrary.controller.BasicRequestParamFilter;
import ca.smartlibrary.dto.library.Library;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockLibraryService implements LibraryService {

    @Override
    public List<Library> findAll(BasicRequestParamFilter filter) {
        return null;
    }
    
}

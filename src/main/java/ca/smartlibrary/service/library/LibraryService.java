package ca.smartlibrary.service.library;

import ca.smartlibrary.controller.BasicRequestParamFilter;
import ca.smartlibrary.dto.library.Library;

import java.util.List;

public interface LibraryService {

    List<Library> findAll(BasicRequestParamFilter filter);

    Library findOne(String id);
}

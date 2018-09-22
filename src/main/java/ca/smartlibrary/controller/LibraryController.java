package ca.smartlibrary.controller;

import ca.smartlibrary.dto.library.Library;
import ca.smartlibrary.service.library.LibraryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libraries")
@Api(tags = {"Library"}, description = "Library search")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    private List<Library> findAll(BasicRequestParamFilter filter){
        return libraryService.findAll(filter);
    }

}

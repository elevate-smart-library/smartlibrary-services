package ca.smartlibrary.service.library;

import ca.smartlibrary.controller.BasicRequestParamFilter;
import ca.smartlibrary.dto.User;
import ca.smartlibrary.dto.library.Library;
import ca.smartlibrary.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MockLibraryService implements LibraryService {

    private final ObjectMapper mapper;
    private final UserService userService;
    private final List<Library> libraries;


    @Autowired
    public MockLibraryService(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
        libraries = findAll();
    }

    @Override
    public List<Library> findAll(BasicRequestParamFilter filter) {
        return libraries.stream()
                .filter(matches(filter))
                .collect(Collectors.toList());
    }

    @Override
    public Library findOne(String id) {
        return libraries.stream()
                .filter(l -> id.equals(l.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find library with id [" + id + "]"));
    }

    private List<Library> findAll() {
        ClassPathResource data = new ClassPathResource("mock/libraries.json");
        try {
            return Arrays.stream(mapper.readValue(data.getInputStream(), Library[].class))
                    .peek(s -> s.getContacts().stream().map(this::attachUserDetail).findFirst().get())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Predicate<Library> matches(BasicRequestParamFilter filter) {
        return (Library l) -> true; // TODO: 2018-09-23  filter on query and nearest location by name and submitted radius

    }

    private User attachUserDetail(User user) {
        return userService.findOne(user.getId());
    }

}

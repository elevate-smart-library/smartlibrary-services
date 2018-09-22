package ca.smartlibrary.service.user;

import ca.smartlibrary.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockUserService implements UserService {

    private final ObjectMapper mapper;

    @Autowired
    public MockUserService(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public User findOne(String id) {
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find user with id [" + id + "]"));
    }

    @Override
    public User update(User source) {
        return null;
    }

    @Override
    public List<User> findAll() {

        ClassPathResource data = new ClassPathResource("mock/users.json");
        try {
            return Arrays.stream(mapper.readValue(data.getInputStream(), User[].class))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

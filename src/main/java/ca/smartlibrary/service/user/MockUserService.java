package ca.smartlibrary.service.user;

import ca.smartlibrary.dto.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MockUserService implements UserService {

    @Override
    public User findOne(String pcId) {
        return null;
    }

    @Override
    public User update(User source) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }
}

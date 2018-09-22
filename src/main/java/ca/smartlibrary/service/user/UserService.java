package ca.smartlibrary.service.user;

import ca.smartlibrary.dto.User;

import java.util.List;

public interface UserService {

    User findOne(String id);

    User update(User source);

    List<User> findAll();
}

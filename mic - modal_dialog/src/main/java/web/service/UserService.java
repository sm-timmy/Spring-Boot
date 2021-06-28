package web.service;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> userList();
    void add(User user);
    void delete(User user);
    void edit(User user);
    User getById(long id);
    User getByLogin(String login);
}

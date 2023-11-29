package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    void removeUserById(Long id);
    List<User> getAllUsers();
    void updateUserById(Long id, User user);
    User getUser(Long id);
    User getUserByName(String username);
}

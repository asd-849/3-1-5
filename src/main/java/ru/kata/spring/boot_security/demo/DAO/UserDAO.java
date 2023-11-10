package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {
    void deleteById(Long id);
    List<User> findAll();
    void save(User user);
    User findById(Long id);
    User findByUsername(String username);
    void update(User user);
}

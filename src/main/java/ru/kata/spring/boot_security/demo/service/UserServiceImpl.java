package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserDAO;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userRepository;
    private RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDAO userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(setUserRoles(user));
    }

    @Transactional
    @Override
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void updateUserById(Long id, User user) {
        User newUser = userRepository.findById(id);
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setAge(user.getAge());
        newUser.setUsername(user.getUsername());
        newUser.setRoles(user.getRoles());
        if (!newUser.getPassword().equals(user.getPassword())) {
            newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(setUserRoles(newUser));
        }
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    private User setUserRoles(User user) {
        var roles = user.getRoles();
        var roleList = roleService.getAllRoles();
        var set = new ArrayList<Role>();
        for (Role role : roleList) {
            for (Role userRole : roles) {
                if (role.getRoleName().equals(userRole.getRoleName())) {
                    set.add(role);
                }
            }
        }
        user.setRoles(set);
        return user;
    }

}

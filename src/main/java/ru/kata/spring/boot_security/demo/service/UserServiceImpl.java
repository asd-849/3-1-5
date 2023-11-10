package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dao.UserDAO;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDAO userRepository;
    private RoleService roleService;
    private PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDAO userRepository, RoleService roleService, @Lazy PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found!");
        }
        return user;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode((user.getPassword())));
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
        user.setPassword(encoder.encode((user.getPassword())));
        userRepository.update(setUserRoles(user));
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

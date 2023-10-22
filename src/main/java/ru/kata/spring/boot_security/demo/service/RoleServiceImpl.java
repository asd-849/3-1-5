package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleDAO;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDAO roleRepository;

    @Autowired
    public RoleServiceImpl(RoleDAO roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public Role getRoleById(Long id) {
        return roleRepository.findById(id);
    }
}

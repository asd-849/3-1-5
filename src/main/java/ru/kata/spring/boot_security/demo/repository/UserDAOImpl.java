package ru.kata.spring.boot_security.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private final EntityManager entityManager;
    @Autowired
    public UserDAOImpl (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void deleteById(Long id) {
        entityManager
                .createQuery("delete from User where id =: userId")
                .setParameter("userId", id)
                .executeUpdate();
    }

    @Override
    public List<User> findAll() {
        return entityManager
                .createQuery("from User", User.class)
                .getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        String query = "select distinct u from User u left join fetch u.roles where u.username =: username";
        return entityManager
                .createQuery(query, User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}

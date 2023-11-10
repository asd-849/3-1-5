package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}

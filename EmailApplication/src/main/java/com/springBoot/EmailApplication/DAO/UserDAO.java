package com.springBoot.EmailApplication.DAO;

import com.springBoot.EmailApplication.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User findUserById(Integer id){
        return entityManager.find(User.class, id);
    }

    public User findUserbyEmailId(String emailId) {
        User userFromDB;
        TypedQuery<User> findUserQuery = entityManager.createQuery(" FROM User WHERE emailId=:emailIdVariable", User.class);
        findUserQuery.setParameter("emailIdVariable", emailId);
        userFromDB = findUserQuery.getSingleResult();
        return userFromDB;
    }

    public User saveUser(User user) {
        return entityManager.merge(user);
    }

    public void deleteUser(User user){
        entityManager.remove(user);
    }

    public List<User> getAllUsers() {
        TypedQuery<User> findUserQuery = entityManager.createQuery("FROM User", User.class);
        return findUserQuery.getResultList();
    }
}

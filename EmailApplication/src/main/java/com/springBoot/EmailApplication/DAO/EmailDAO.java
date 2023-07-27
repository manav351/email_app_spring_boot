package com.springBoot.EmailApplication.DAO;

import com.springBoot.EmailApplication.Entity.Email;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailDAO {

    private EntityManager entityManager;

    @Autowired
    public EmailDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Email findEmailById(Integer id){
        return entityManager.find(Email.class, id);
    }

    public List<Email> findEmailbyEmailId(String emailId) {
        TypedQuery<Email> findEmailQuery = entityManager.createQuery(" FROM Email WHERE emailId=:emailIdVariable", Email.class);
        findEmailQuery.setParameter("emailIdVariable", emailId);
        return findEmailQuery.getResultList();
    }

    public Email saveEmail(Email email) {
        return entityManager.merge(email);
    }

    public void deleteEmail(Email email){
        entityManager.remove(email);
    }

    public List<Email> getAllEmails() {
        TypedQuery<Email> findEmailQuery = entityManager.createQuery("FROM Email", Email.class);
        return findEmailQuery.getResultList();
    }
}

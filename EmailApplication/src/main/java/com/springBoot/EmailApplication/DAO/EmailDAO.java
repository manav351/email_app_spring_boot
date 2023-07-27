package com.springBoot.EmailApplication.DAO;

import com.springBoot.EmailApplication.Entity.Email;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmailDAO {

    private EntityManager entityManager;

    @Autowired
    public EmailDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Email getEmailbyEmailId(String emailId) {
        TypedQuery<Email> findEmailQuery = entityManager.createQuery(" FROM Email WHERE emailId=:emailIdVariable", Email.class);
        findEmailQuery.setParameter("emailIdVariable", emailId);
        return findEmailQuery.getSingleResult();
    }
}

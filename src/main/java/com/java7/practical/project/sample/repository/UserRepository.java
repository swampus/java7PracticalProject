package com.java7.practical.project.sample.repository;

import com.java7.practical.project.sample.HibernateUtil;

import com.java7.practical.project.sample.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepository {
    public User saveUser(User user){
        Session session = HibernateUtil.getSessionFactory()
                .getCurrentSession();
        Transaction trn = HibernateUtil.getSessionFactory()
                .getCurrentSession().beginTransaction();
        long id = (long) session.save(user);
        user.setId(id);
        trn.commit();
        return user;
    }
}

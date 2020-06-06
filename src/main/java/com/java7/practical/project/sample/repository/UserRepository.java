package com.java7.practical.project.sample.repository;

import com.java7.practical.project.sample.HibernateUtil;

import com.java7.practical.project.sample.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

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

    public List<User> getlAllUser(){
        Session session = HibernateUtil.getSessionFactory()
                .getCurrentSession();
        Transaction trn = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        List<User> users = session.createQuery(criteria).getResultList();
        trn.commit();
        return users;

    }
}

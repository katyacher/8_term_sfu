package edu.sfu.lab6.dao;

import edu.sfu.lab6.manager.DAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import java.util.List;

public abstract class BaseDAO<T> {
    private final Class<T> entityClass;

    protected BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> getAll() {
        try {
            DAO.begin();
            Session session = DAO.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            criteria.from(entityClass);
            List<T> result = session.createQuery(criteria).getResultList();
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
}
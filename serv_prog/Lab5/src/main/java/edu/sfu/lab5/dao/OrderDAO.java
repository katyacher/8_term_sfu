package edu.sfu.lab5.dao;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class OrderDAO extends BaseDAO<Order> {
    public OrderDAO() {
        super(Order.class);
    }
    
    public Order getById(Integer id) {
        Session session = DAO.getSession();
        try {
            return session.get(Order.class, id);
        } finally {
            session.close();
        }
    }

    public List<Order> getAll() {
        Session session = DAO.getSession();
        try {
            return session.createQuery("FROM Order", Order.class).getResultList();
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(Order order) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(order);
            transaction.commit();
            return id;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("deprecation")
	public void update(Order order) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("deprecation")
	public void delete(Integer id) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                session.delete(order);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
package edu.sfu.lab5.dao;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;


public class CustomerDAO extends BaseDAO<Customer> {
    public CustomerDAO() {
        super(Customer.class);
    }
    
    public Customer getById(Integer id) {
        Session session = DAO.getSession();
        try {
            return session.get(Customer.class, id);
        } finally {
            session.close();
        }
    }

    public List<Customer> getAll() {
        Session session = DAO.getSession();
        try {
            return session.createQuery("FROM Customer", Customer.class).getResultList();
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(Customer customer) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(customer);
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
	public void update(Customer customer) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(customer);
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
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
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
package edu.sfu.lab5.dao;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Customer;
import java.util.List;

public class CustomerDAO extends BaseDAO<Customer> {
    public CustomerDAO() {
        super(Customer.class);
    }
    
    public Customer getById(Integer id) {
        try {
            DAO.begin();
            Customer customer = DAO.getSession().get(Customer.class, id);
            DAO.commit();
            return customer;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    public List<Customer> getAll() {
        try {
            DAO.begin();
            List<Customer> customers = DAO.getSession()
                .createQuery("FROM Customer", Customer.class)
                .getResultList();
            DAO.commit();
            return customers;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(Customer customer) {
        try {
            DAO.begin();
            Integer id = (Integer) DAO.getSession().save(customer);
            DAO.commit();
            return id;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public void update(Customer customer) {
        try {
            DAO.begin();
            DAO.getSession().update(customer);
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public void delete(Integer id) {
        try {
            DAO.begin();
            Customer customer = DAO.getSession().get(Customer.class, id);
            if (customer != null) {
                DAO.getSession().delete(customer);
            }
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
}
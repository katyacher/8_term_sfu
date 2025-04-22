package edu.sfu.lab6.dao;

import edu.sfu.lab6.manager.DAO;
import edu.sfu.lab6.model.Order;
import java.util.List;

public class OrderDAO extends BaseDAO<Order> {
    public OrderDAO() {
        super(Order.class);
    }
    
    public Order getById(Integer id) {
        try {
            DAO.begin();
            Order order = DAO.getSession().get(Order.class, id);
            DAO.commit();
            return order;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    public List<Order> getAll() {
        try {
            DAO.begin();
            List<Order> orders = DAO.getSession()
                .createQuery("FROM Order", Order.class)
                .getResultList();
            DAO.commit();
            return orders;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(Order order) {
        try {
            DAO.begin();
            Integer id = (Integer) DAO.getSession().save(order);
            DAO.commit();
            return id;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public void update(Order order) {
        try {
            DAO.begin();
            DAO.getSession().update(order);
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
            Order order = DAO.getSession().get(Order.class, id);
            if (order != null) {
                DAO.getSession().delete(order);
            }
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
}
package edu.sfu.lab5.dao;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.JewelryType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class JewelryTypeDAO extends BaseDAO<JewelryType> {
    public JewelryTypeDAO() {
        super(JewelryType.class);
    }
    public JewelryType getById(Integer id) {
        Session session = DAO.getSession();
        try {
            return session.get(JewelryType.class, id);
        } finally {
            session.close();
        }
    }

    public List<JewelryType> getAll() {
        Session session = DAO.getSession();
        try {
            return session.createQuery("FROM JewelryType", JewelryType.class).getResultList();
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(JewelryType jewelryType) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(jewelryType);
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
	public void update(JewelryType jewelryType) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(jewelryType);
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
            JewelryType jewelryType = session.get(JewelryType.class, id);
            if (jewelryType != null) {
                session.delete(jewelryType);
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
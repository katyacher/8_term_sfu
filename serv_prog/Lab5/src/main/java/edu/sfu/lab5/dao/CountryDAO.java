package edu.sfu.lab5.dao;


import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Country;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CountryDAO extends BaseDAO<Country> {
    public CountryDAO() {
        super(Country.class);
    }
    

    public Country getById(Integer id) {
        Session session = DAO.getSession();
        try {
            return session.get(Country.class, id);
        } finally {
            session.close();
        }
    }

    public List<Country> getAll() {
        Session session = DAO.getSession();
        try {
            return session.createQuery("FROM Country", Country.class).getResultList();
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(Country country) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(country);
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
	public void update(Country country) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(country);
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
            Country country = session.get(Country.class, id);
            if (country != null) {
                session.delete(country);
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
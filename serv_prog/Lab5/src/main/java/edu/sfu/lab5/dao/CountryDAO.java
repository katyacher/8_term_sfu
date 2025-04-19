package edu.sfu.lab5.dao;


import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Country;
import org.hibernate.Session;
import java.util.List;

public class CountryDAO extends BaseDAO<Country> {
    public CountryDAO() {
        super(Country.class);
    }
    

    public Country getById(Integer id) {
    	 try {
             DAO.begin();
             Country country = DAO.getSession().get(Country.class, id);
             DAO.commit();
             return country;
         } catch (Exception e) {
             DAO.rollback();
             throw e;
         }
    }

    public List<Country> getAll() {
        try {
        	DAO.begin();
        	List<Country> countries = DAO.getSession().createQuery("FROM Country", Country.class).getResultList();
        	DAO.commit();
            return countries;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(Country country) {
    	try {
            DAO.begin();
            Integer id = (Integer) DAO.getSession().save(country);
            DAO.commit();
            return id;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public void update(Country country) {
        try {
            DAO.begin();
            DAO.getSession().update(country);
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
            Session session = DAO.getSession();
            Country country = session.get(Country.class, id);
            if (country != null) {
                session.delete(country);
            }
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
}
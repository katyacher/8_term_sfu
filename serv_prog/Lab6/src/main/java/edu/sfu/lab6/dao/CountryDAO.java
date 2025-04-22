package edu.sfu.lab6.dao;

import edu.sfu.lab6.model.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDAO extends BaseDAO<Country> {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public CountryDAO() {
        super(Country.class);
    }
    
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Country getById(Integer id) {
        return getSession().get(Country.class, id);
    }

    public List<Country> getAll() {
        return getSession().createQuery("FROM Country", Country.class).getResultList();
    }

    @SuppressWarnings("deprecation")
	public Integer save(Country country) {
        return (Integer) getSession().save(country);
    }

    @SuppressWarnings("deprecation")
	public void update(Country country) {
        getSession().update(country);
    }

    @SuppressWarnings("deprecation")
	public void delete(Integer id) {
        Country country = getSession().get(Country.class, id);
        if (country != null) {
            getSession().delete(country);
        }
    }
}
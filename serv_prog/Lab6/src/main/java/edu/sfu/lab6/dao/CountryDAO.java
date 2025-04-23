package edu.sfu.lab6.dao;

import edu.sfu.lab6.model.Country;

import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public class CountryDAO extends BaseDAO<Country> {
  
    public CountryDAO() {
        super(Country.class);
    }
    @Override
    public List<Country> getAll() {
        try {
            return getSession().createQuery("FROM Country", Country.class).list();
        } catch (Exception e) {
        	System.out.println("Error in getAll()");
            throw e;
        }
    }
    @SuppressWarnings("deprecation")
	public Integer save(Country country) {
        return (Integer) getSession().save(country);
    }

	public void update(Country country) {
        getSession().merge(country);
    }


	public void delete(Integer id) {
        Country country = getSession().get(Country.class, id);
        if (country != null) {
            getSession().remove(country);
        }
    }
}
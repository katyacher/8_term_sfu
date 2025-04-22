package edu.sfu.lab6.dao;

import edu.sfu.lab6.model.Country;
import org.springframework.stereotype.Repository;


@Repository
public class CountryDAO extends BaseDAO<Country> {
  
    public CountryDAO() {
        super(Country.class);
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
package edu.sfu.lab6.services;

import edu.sfu.lab6.dao.CountryDAO;
import edu.sfu.lab6.model.Country;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountryService {

    private final CountryDAO countryDAO;

    public CountryService(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    public List<Country> getAllCountries() {
        return countryDAO.getAll();
    }

    public Country getCountryById(Integer id) {
        return countryDAO.getById(id);
    }

    public Country createCountry(Country country) {
        Integer id = countryDAO.save(country);
        return countryDAO.getById(id);
    }

    public Country updateCountry(Integer id, Country country) {
        Country existing = countryDAO.getById(id);
        if (existing != null) {
            existing.setName(country.getName());
            existing.setCode(country.getCode());
            countryDAO.update(existing);
        }
        return existing;
    }

    public void deleteCountry(Integer id) {
        countryDAO.delete(id);
    }
}
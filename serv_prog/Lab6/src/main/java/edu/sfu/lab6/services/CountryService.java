package edu.sfu.lab6.services;

import edu.sfu.lab6.dao.CountryRepository;
import edu.sfu.lab6.model.Country;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(Integer id) {
        return countryRepository.findById(id).orElse(null);
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Integer id, Country country) {
        Country existing = countryRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(country.getName());
            existing.setCode(country.getCode());
            return countryRepository.save(existing);
        }
        return null;
    }

    public void deleteCountry(Integer id) {
        countryRepository.deleteById(id);
    }
}
package edu.sfu.lab6.controllers;

import edu.sfu.lab6.dao.CountryDAO;
import edu.sfu.lab6.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    
    private final CountryDAO countryDAO;

    @Autowired
    public MainController(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return countryDAO.getAll();
    }
}
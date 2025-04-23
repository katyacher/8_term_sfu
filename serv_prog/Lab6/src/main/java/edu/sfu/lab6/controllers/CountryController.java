package edu.sfu.lab6.controllers;

import edu.sfu.lab6.model.Country;
import edu.sfu.lab6.services.CountryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")  // Базовый путь 
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")  // Полный путь  /api/countries
    public ResponseEntity<?> getAllCountries() {
    	 List<Country> countries = countryService.getAllCountries();
    	 return ResponseEntity.ok(countries);
    }

    @GetMapping("/countries/{id}")
    public Country getCountryById(@PathVariable Integer id) {
        return countryService.getCountryById(id);
    }

    @PostMapping("/countries")
    public Country createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

    @PutMapping("/countries/{id}")
    public Country updateCountry(@PathVariable Integer id, @RequestBody Country country) {
        return countryService.updateCountry(id, country);
    }

    @DeleteMapping("/countries/{id}")
    public void deleteCountry(@PathVariable Integer id) {
        countryService.deleteCountry(id);
    }
}
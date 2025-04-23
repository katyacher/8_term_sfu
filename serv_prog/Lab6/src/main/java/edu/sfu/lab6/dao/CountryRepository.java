package edu.sfu.lab6.dao;


import edu.sfu.lab6.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>  {
	 // Все базовые CRUD операции уже предоставляются JpaRepository:
		// save() - сохранение сущности
		//findById() - поиск по ID
		//findAll() - получение всех записей
		//deleteById() - удаление по ID
		//count() - количество записей
   
	 // Найти по коду страны
    Country findByCode(String code);
    
    // Найти все страны, где имя содержит строку (без учета регистра)
    List<Country> findByNameContainingIgnoreCase(String namePart);
    
}
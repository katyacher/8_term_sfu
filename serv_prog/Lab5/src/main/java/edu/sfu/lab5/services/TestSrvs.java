package edu.sfu.lab5.services;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Country;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;

import java.util.List;

import org.hibernate.Session;

public class TestSrvs {
    
    // Получение имени страны по ID с использованием Criteria API
    public String getName(int id) {
    	System.out.println("DEBUG: Getting country with id=" + id);
        try {
            DAO.begin();
            System.out.println("DEBUG: Transaction started");
            Session session = DAO.getSession();
            

            // Проверка метамодели
            Metamodel metamodel = session.getMetamodel();
            EntityType<Country> countryEntity = metamodel.entity(Country.class);
            System.out.println("Country entity detected: " + countryEntity);
            
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> criteria = builder.createQuery(String.class);
            Root<Country> root = criteria.from(Country.class);
            
            criteria.select(root.get("name"))
                   .where(builder.equal(root.get("id"), id));
            
            String result = session.createQuery(criteria).uniqueResult();
            DAO.commit();
            return result;
        } catch (Exception e) {
        	System.err.println("ERROR in getName: " + e.getMessage());
            DAO.rollback();
            throw e;
        }
    }

    // Получение списка имен в диапазоне ID с использованием Criteria API
    public List<String> getNamesInRange(int startId, int endId) {
        try {
            DAO.begin();
            Session session = DAO.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            
            CriteriaQuery<String> criteria = builder.createQuery(String.class);
            Root<Country> root = criteria.from(Country.class);
            
            criteria.select(root.get("name"))
                   .where(builder.between(root.get("id"), startId, endId));
            
            List<String> result = session.createQuery(criteria).getResultList();
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    // Создание новой записи
    public int createCountry(String name, String code) {
        try {
            DAO.begin();
            Session session = DAO.getSession();
            
            Country country = new Country();
            country.setName(name);
            country.setCode(code);
            
            session.persist(country);
            DAO.commit();
            return 1;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
}
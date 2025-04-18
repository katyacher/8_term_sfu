package edu.sfu.lab5.services;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Country;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import java.util.List;

public class TestSrvs extends DAO {
    
    // Получение имени страны по ID с использованием Criteria API
    public static String getName(int id) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<Country> root = criteria.from(Country.class);
        
        criteria.select(root.get("name"))
               .where(builder.equal(root.get("id"), id));
        
        return session.createQuery(criteria).uniqueResult();
    }

    // Получение списка имен в диапазоне ID с использованием Criteria API
    public static List<String> getNamesInRange(int startId, int endId) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<Country> root = criteria.from(Country.class);
        
        criteria.select(root.get("name"))
               .where(builder.between(root.get("id"), startId, endId));
        
        return session.createQuery(criteria).getResultList();
    }

    // Создание новой записи с использованием Hibernate Entity Manager
    public static int createCountry(String name, String code) {
        begin();
        Session session = getSession();
        
        Country country = new Country();
        country.setName(name);
        country.setCode(code);
        
        session.persist(country);
        commit();
        
        return 1; // Возвращаем 1, так как одна запись была создана
    }
    
}

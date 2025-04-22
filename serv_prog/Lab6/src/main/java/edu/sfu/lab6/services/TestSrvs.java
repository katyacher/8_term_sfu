package edu.sfu.lab6.services;

import edu.sfu.lab6.dao.CountryDAO;
import edu.sfu.lab6.model.Country;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TestSrvs {

    private final SessionFactory sessionFactory;
    private final CountryDAO countryDAO;

    @Autowired
    public TestSrvs(SessionFactory sessionFactory, CountryDAO countryDAO) {
        this.sessionFactory = sessionFactory;
        this.countryDAO = countryDAO;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public String getName(int id) {
        Session session = getSession();
        
        // Проверка метамодели (можно оставить для диагностики)
        Metamodel metamodel = session.getMetamodel();
        EntityType<Country> countryEntity = metamodel.entity(Country.class);
        System.out.println("Country entity detected: " + countryEntity);
        
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<Country> root = criteria.from(Country.class);
        
        criteria.select(root.get("name"))
               .where(builder.equal(root.get("id"), id));
        
        return session.createQuery(criteria).uniqueResult();
    }

    public List<String> getNamesInRange(int startId, int endId) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<Country> root = criteria.from(Country.class);
        
        criteria.select(root.get("name"))
               .where(builder.between(root.get("id"), startId, endId));
        
        return session.createQuery(criteria).getResultList();
    }

    public int createCountry(String name, String code) {
        Country country = new Country();
        country.setName(name);
        country.setCode(code);
        
        countryDAO.save(country);
        return 1;
    }
}
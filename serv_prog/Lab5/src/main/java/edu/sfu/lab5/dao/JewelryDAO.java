package edu.sfu.lab5.dao;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.*;
import jakarta.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;

public class JewelryDAO extends BaseDAO<Jewelry> {
    public JewelryDAO() {
        super(Jewelry.class);
    }
    
    public List<Jewelry> findWithFilters(String name, 
                                      BigDecimal minPrice, 
                                      BigDecimal maxPrice, 
                                      Integer typeId,
                                      String manufacturer,
                                      int firstResult, int maxResults) {
        try {
            DAO.begin();
            Session session = DAO.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Jewelry> criteria = builder.createQuery(Jewelry.class);
            Root<Jewelry> root = criteria.from(Jewelry.class);
            
            List<Predicate> predicates = new ArrayList<>();
            
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(
                    builder.lower(root.get("name")), 
                    "%" + name.toLowerCase() + "%"
                ));
            }
            
            if (minPrice != null) {
                predicates.add(builder.greaterThanOrEqualTo(
                    root.get("price"), 
                    minPrice
                ));
            }
            
            if (maxPrice != null) {
                predicates.add(builder.lessThanOrEqualTo(
                    root.get("price"), 
                    maxPrice
                ));
            }
            
            if (typeId != null) {
                predicates.add(builder.equal(
                    root.get("type").get("id"), 
                    typeId
                ));
            }
            
            if (manufacturer != null && !manufacturer.isEmpty()) {
                predicates.add(builder.like(
                    builder.lower(root.get("manufacturer")), 
                    "%" + manufacturer.toLowerCase() + "%"
                ));
            }
            
            if (!predicates.isEmpty()) {
                criteria.where(builder.and(
                    predicates.toArray(new Predicate[0])
                ));
            }
            
            criteria.orderBy(builder.asc(root.get("name")));
            
            List<Jewelry> result = session.createQuery(criteria)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
    
    public List<Object[]> findExpensiveGemstoneJewelry(int firstResult, int maxResults) {
        try {
            DAO.begin();
            Session session = DAO.getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
            
            Root<Jewelry> jewelry = query.from(Jewelry.class);
            Join<Jewelry, Material> materialJoin = jewelry.join("materials", JoinType.INNER);
            
            Subquery<Integer> countrySubquery = query.subquery(Integer.class);
            Root<Country> country = countrySubquery.from(Country.class);
            countrySubquery.select(country.get("id"))
                         .where(country.get("name").in(Arrays.asList("Россия", "Италия", "Франция")));
            
            query.multiselect(
                jewelry.get("id").alias("jewelry_id"),
                jewelry.get("name").alias("jewelry_name"),
                jewelry.get("price"),
                jewelry.get("weight"),
                materialJoin.get("name").alias("material_name"),
                materialJoin.get("type").alias("material_type"),
                materialJoin.get("carat")
            );
            
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.between(jewelry.get("price"), 
                new BigDecimal("500"), 
                new BigDecimal("5000")));
            predicates.add(cb.equal(materialJoin.get("type"), "gemstone"));
            predicates.add(cb.greaterThan(jewelry.get("weight"), 
                new BigDecimal("10")));
            predicates.add(jewelry.get("country").get("id").in(countrySubquery));
            
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            query.orderBy(cb.desc(jewelry.get("price")));
            
            List<Object[]> result = session.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
    
    // CRUD операции
    public Jewelry getById(Integer id) {
        try {
            DAO.begin();
            Jewelry jewelry = DAO.getSession().get(Jewelry.class, id);
            DAO.commit();
            return jewelry;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    public List<Jewelry> getAll() {
        try {
            DAO.begin();
            List<Jewelry> result = DAO.getSession()
                .createQuery("FROM Jewelry", Jewelry.class)
                .getResultList();
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(Jewelry jewelry) {
        try {
            DAO.begin();
            Integer id = (Integer) DAO.getSession().save(jewelry);
            DAO.commit();
            return id;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    public void update(Jewelry jewelry) {
        try {
            DAO.begin();
            DAO.getSession().merge(jewelry);
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    public void delete(Integer id) {
        try {
            DAO.begin();
            Jewelry jewelry = DAO.getSession().get(Jewelry.class, id);
            if (jewelry != null) {
                DAO.getSession().remove(jewelry);
            }
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public void saveOrUpdate(Jewelry jewelry) {
        try {
            DAO.begin();
            DAO.getSession().saveOrUpdate(jewelry);
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
    
    public List<Jewelry> findByCountryId(Integer countryId) {
        try {
            DAO.begin();
            Session session = DAO.getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Jewelry> cq = cb.createQuery(Jewelry.class);
            Root<Jewelry> root = cq.from(Jewelry.class);
            
            cq.where(cb.equal(root.get("country").get("id"), countryId));
            cq.orderBy(cb.asc(root.get("name")));
            
            List<Jewelry> result = session.createQuery(cq).getResultList();
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    public List<Jewelry> findByCountryIdWithPagination(Integer countryId, int page, int size) {
        try {
            DAO.begin();
            Session session = DAO.getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Jewelry> cq = cb.createQuery(Jewelry.class);
            Root<Jewelry> root = cq.from(Jewelry.class);
            
            cq.where(cb.equal(root.get("country").get("id"), countryId));
            cq.orderBy(cb.asc(root.get("id")));
            
            List<Jewelry> result = session.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
    
    public List<Jewelry> findFirstNByCountryId(Integer countryId, int limit) {
        try {
            DAO.begin();
            Session session = DAO.getSession();
            
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Jewelry> cq = cb.createQuery(Jewelry.class);
            Root<Jewelry> root = cq.from(Jewelry.class);
            
            // Явно подгружаем связанные сущности
            root.fetch("materials", JoinType.LEFT);
            root.fetch("type", JoinType.LEFT);
            root.fetch("country", JoinType.LEFT);
            
            cq.where(cb.equal(root.get("country").get("id"), countryId));
            cq.orderBy(cb.asc(root.get("id")));
            
            List<Jewelry> result = session.createQuery(cq)
                .setMaxResults(limit)
                .getResultList();
                
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
}
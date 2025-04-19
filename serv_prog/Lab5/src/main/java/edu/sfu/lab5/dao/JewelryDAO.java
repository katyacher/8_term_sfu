package edu.sfu.lab5.dao;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.*;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JewelryDAO extends BaseDAO<Jewelry> { //работа с БД
    public JewelryDAO() {
        super(Jewelry.class);
    }
    
    
 // Метод с динамическими фильтрами
    public List<Jewelry> findWithFilters(String name, 
                                       BigDecimal minPrice, 
                                       BigDecimal maxPrice, 
                                       Integer typeId,
                                       String manufacturer,
                                       int firstResult, int maxResults) {
        Session session = DAO.getSession();
        try {
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<Jewelry> criteria = builder.createQuery(Jewelry.class);
	        Root<Jewelry> root = criteria.from(Jewelry.class);
	        
	        // Список для хранения условий
	        List<Predicate> predicates = new ArrayList<>();
	        
	        // Добавляем условия только если параметры не null
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
	        
	        // Комбинируем все условия с AND
	        if (!predicates.isEmpty()) {
	            criteria.where(builder.and(
	                predicates.toArray(new Predicate[0])
	            ));
	        }
	        
	        // Сортировка по имени по умолчанию
	        criteria.orderBy(builder.asc(root.get("name")));
        
	        return session.createQuery(criteria)
	        		.setFirstResult(firstResult)
	                .setMaxResults(maxResults)
	                .getResultList();
        } finally {
            session.close();
        }
    }
    
    
    // Метод для выполнения запроса получения дорогих украшений с камнями производства России, Италии или Франции
    public List<Object[]> findExpensiveGemstoneJewelry(int firstResult, int maxResults) {
        Session session = DAO.getSession();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
            
            // Основные корневые сущности
            Root<Jewelry> jewelry = query.from(Jewelry.class);
            
            // Join с Material через many-to-many связь
            Join<Jewelry, Material> materialJoin = jewelry.join("materials", JoinType.INNER);
            
            // Подзапрос для стран
            Subquery<Integer> countrySubquery = query.subquery(Integer.class);
            Root<Country> country = countrySubquery.from(Country.class);
            countrySubquery.select(country.get("id"))
                         .where(country.get("name").in(Arrays.asList("Россия", "Италия", "Франция")));
            
            // Строим запрос
            query.multiselect(
                jewelry.get("id").alias("jewelry_id"),
                jewelry.get("name").alias("jewelry_name"),
                jewelry.get("price"),
                jewelry.get("weight"),
                materialJoin.get("name").alias("material_name"),
                materialJoin.get("type").alias("material_type"),
                materialJoin.get("carat")
            );
            
            // Условия WHERE
            List<Predicate> predicates = new ArrayList<>();
            
            // Цена между 500 и 5000
            predicates.add(cb.between(jewelry.get("price"), 
                new BigDecimal("500"), 
                new BigDecimal("5000")));
            
            // Только драгоценные камни
            predicates.add(cb.equal(materialJoin.get("type"), "gemstone"));
            
            // Вес больше 10
            predicates.add(cb.greaterThan(jewelry.get("weight"), 
                new BigDecimal("10")));
            
            // Страна производства из списка
            predicates.add(jewelry.get("country").get("id").in(countrySubquery));
            
            // Комбинируем все условия
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            
            // Сортировка по убыванию цены
            query.orderBy(cb.desc(jewelry.get("price")));
            
            // Выполняем запрос с пагинацией
            return session.createQuery(query)
                        .setFirstResult(firstResult)
                        .setMaxResults(maxResults)
                        .getResultList();
        } finally {
            session.close();
        }
    }
    
  // CRUD операции
 // 1. Получение сущности по ID
    public Jewelry getById(Integer id) {
        Session session = DAO.getSession();
        try {
            return session.get(Jewelry.class, id);
        } finally {
            session.close();
        }
    }

    // 2. Получение всех сущностей
    public List<Jewelry> getAll() {
        Session session = DAO.getSession();
        try {
            return session.createQuery("FROM Jewelry", Jewelry.class).getResultList();
        } finally {
            session.close();
        }
    }

    // 3. Сохранение новой сущности
    @SuppressWarnings("deprecation")
	public Integer save(Jewelry jewelry) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(jewelry);
            transaction.commit();
            return id;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    // 4. Обновление существующей сущности
	public void update(Jewelry jewelry) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(jewelry);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    // 5. Удаление сущности
 
	public void delete(Integer id) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Jewelry jewelry = session.get(Jewelry.class, id);
            if (jewelry != null) {
                session.remove(jewelry);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    // 6. Сохранение или обновление (если ID существует)
    @SuppressWarnings("deprecation")
	public void saveOrUpdate(Jewelry jewelry) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(jewelry);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    // 7. Демонстрационный метод для поиска по стране с Criteria API
    public List<Jewelry> findByCountryId(Integer countryId) {
	    Session session = DAO.getSession();
	    try {
	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<Jewelry> cq = cb.createQuery(Jewelry.class);
	        Root<Jewelry> root = cq.from(Jewelry.class);
	        
	        // Добавляем условие для страны
	        cq.where(cb.equal(root.get("country").get("id"), countryId));
	        
	        // Сортировка по имени
	        cq.orderBy(cb.asc(root.get("name")));
	        
	        return session.createQuery(cq).getResultList();
	    } finally {
	        session.close();
	    }
    }
    // 7.1. Демонстрационный метод для поиска по стране с Criteria API с пагинацией
    public List<Jewelry> findByCountryIdWithPagination(Integer countryId, int page, int size) {
        Session session = DAO.getSession();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Jewelry> cq = cb.createQuery(Jewelry.class);
            Root<Jewelry> root = cq.from(Jewelry.class);
            
            cq.where(cb.equal(root.get("country").get("id"), countryId));
            cq.orderBy(cb.asc(root.get("id")));
            
            return session.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        } finally {
            session.close();
        }
    }
}
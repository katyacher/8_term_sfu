package edu.sfu.lab6.dao;

import edu.sfu.lab6.model.Order;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderDAO extends BaseDAO<Order> {

    public OrderDAO() {
        super(Order.class);
    }

    @SuppressWarnings("deprecation")
	public Integer save(Order order) {
        return (Integer) getSession().save(order);
    }

    public void update(Order order) {
        getSession().merge(order);
    }

    public void delete(Integer id) {
        Order order = getSession().get(Order.class, id);
        if (order != null) {
            getSession().remove(order);
        }
    }
    
    public List<Order> findByCustomerId(Integer customerId) {
        Session session = getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);

        // Подгружаем связанные сущности для избежания N+1 проблемы
        root.fetch("customer", JoinType.LEFT);
        root.fetch("items", JoinType.LEFT)
           .fetch("jewelry", JoinType.LEFT);

        cq.where(cb.equal(root.get("customer").get("id"), customerId));
        cq.orderBy(cb.desc(root.get("orderDate")));

        return session.createQuery(cq).getResultList();
    }
 
}
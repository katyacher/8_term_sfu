package edu.sfu.lab6.dao;

import edu.sfu.lab6.model.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerDAO extends BaseDAO<Customer> {

    public CustomerDAO() {
        super(Customer.class);
    }

    public Customer findByEmail(String email) {
        Session session = getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);

        cq.where(cb.equal(root.get("email"), email));
        return session.createQuery(cq).uniqueResult();
    }

    public Customer findByPhone(String phone) {
        Session session = getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);

        cq.where(cb.equal(root.get("phone"), phone));
        return session.createQuery(cq).uniqueResult();
    }

    @SuppressWarnings("deprecation")
	public Integer save(Customer customer) {
        return (Integer) getSession().save(customer);
    }

    public void update(Customer customer) {
        getSession().merge(customer);
    }

    public void delete(Integer id) {
        Customer customer = getSession().get(Customer.class, id);
        if (customer != null) {
            getSession().remove(customer);
        }
    }
}
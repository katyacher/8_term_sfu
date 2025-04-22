package edu.sfu.lab6.dao;

import edu.sfu.lab6.model.Material;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class MaterialDAO extends BaseDAO<Material> {

    public MaterialDAO() {
        super(Material.class);
    }

    @SuppressWarnings("deprecation")
	public Integer save(Material material) {
        return (Integer) getSession().save(material);
    }

    public void update(Material material) {
        getSession().merge(material);
    }

    public void delete(Integer id) {
        Material material = getSession().get(Material.class, id);
        if (material != null) {
            getSession().remove(material);
        }
    }
    
    public List<Material> findByType(String type) {
        Session session = getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Material> cq = cb.createQuery(Material.class);
        Root<Material> root = cq.from(Material.class);

        cq.where(cb.equal(root.get("type"), type));
        cq.orderBy(cb.asc(root.get("name")));

        return session.createQuery(cq).getResultList();
    }
}
package edu.sfu.lab5.dao;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Material;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MaterialDAO extends BaseDAO<Material> {
    public MaterialDAO() {
        super(Material.class);
    }
    public Material getById(Integer id) {
        Session session = DAO.getSession();
        try {
            return session.get(Material.class, id);
        } finally {
            session.close();
        }
    }

    public List<Material> getAll() {
        Session session = DAO.getSession();
        try {
            return session.createQuery("FROM Material", Material.class).getResultList();
        } finally {
            session.close();
        }
    }

    public Integer save(Material material) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            @SuppressWarnings("deprecation")
			Integer id = (Integer) session.save(material);
            transaction.commit();
            return id;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("deprecation")
	public void update(Material material) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(material);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("deprecation")
	public void delete(Integer id) {
        Session session = DAO.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Material material = session.get(Material.class, id);
            if (material != null) {
                session.delete(material);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
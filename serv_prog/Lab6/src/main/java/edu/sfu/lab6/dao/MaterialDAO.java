package edu.sfu.lab6.dao;

import edu.sfu.lab6.manager.DAO;
import edu.sfu.lab6.model.Material;
import java.util.List;

public class MaterialDAO extends BaseDAO<Material> {
    public MaterialDAO() {
        super(Material.class);
    }

    public Material getById(Integer id) {
        try {
            DAO.begin();
            Material material = DAO.getSession().get(Material.class, id);
            DAO.commit();
            return material;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    public List<Material> getAll() {
        try {
            DAO.begin();
            List<Material> materials = DAO.getSession()
                .createQuery("FROM Material", Material.class)
                .getResultList();
            DAO.commit();
            return materials;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(Material material) {
        try {
            DAO.begin();
            Integer id = (Integer) DAO.getSession().save(material);
            DAO.commit();
            return id;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public void update(Material material) {
        try {
            DAO.begin();
            DAO.getSession().update(material);
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public void delete(Integer id) {
        try {
            DAO.begin();
            Material material = DAO.getSession().get(Material.class, id);
            if (material != null) {
                DAO.getSession().delete(material);
            }
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
}
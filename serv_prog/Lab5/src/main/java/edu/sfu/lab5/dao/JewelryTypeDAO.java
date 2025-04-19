package edu.sfu.lab5.dao;

import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.JewelryType;
import java.util.List;

public class JewelryTypeDAO extends BaseDAO<JewelryType> {
    public JewelryTypeDAO() {
        super(JewelryType.class);
    }

    public JewelryType getById(Integer id) {
        try {
            DAO.begin();
            JewelryType jewelryType = DAO.getSession().get(JewelryType.class, id);
            DAO.commit();
            return jewelryType;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    public List<JewelryType> getAll() {
        try {
            DAO.begin();
            List<JewelryType> result = DAO.getSession()
                .createQuery("FROM JewelryType", JewelryType.class)
                .getResultList();
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public Integer save(JewelryType jewelryType) {
        try {
            DAO.begin();
            Integer id = (Integer) DAO.getSession().save(jewelryType);
            DAO.commit();
            return id;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
	public void update(JewelryType jewelryType) {
        try {
            DAO.begin();
            DAO.getSession().update(jewelryType);
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
            JewelryType jewelryType = DAO.getSession().get(JewelryType.class, id);
            if (jewelryType != null) {
                DAO.getSession().delete(jewelryType);
            }
            DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }
}
package edu.sfu.lab6.dao;

import edu.sfu.lab6.model.JewelryType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class JewelryTypeDAO extends BaseDAO<JewelryType> {

    public JewelryTypeDAO() {
        super(JewelryType.class);
    }

    @SuppressWarnings("deprecation")
	public Integer save(JewelryType jewelryType) {
        return (Integer) getSession().save(jewelryType);
    }

    public void update(JewelryType jewelryType) {
        getSession().merge(jewelryType);
    }

    public void delete(Integer id) {
        JewelryType jewelryType = getSession().get(JewelryType.class, id);
        if (jewelryType != null) {
            getSession().remove(jewelryType);
        }
    }
}
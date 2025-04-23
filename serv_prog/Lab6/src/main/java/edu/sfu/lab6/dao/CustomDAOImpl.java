package edu.sfu.lab6.dao;

import edu.sfu.lab6.model.CustomClass;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@SuppressWarnings("deprecation")
@Repository
public class CustomDAOImpl implements CustomDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
	@Override
    public List<CustomClass> getCombinedData() {
    	String sql = "SELECT j.id as jewelryId, j.name as jewelryName, j.price as price, " +
                "c.name as countryName, c.code as countryCode " +
                "FROM jewelry j LEFT JOIN country c ON j.country_id = c.id " +
                "LIMIT 10"; // Добавляем LIMIT для PostgreSQL
        
        return entityManager.createNativeQuery(sql)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("jewelryId", StandardBasicTypes.INTEGER)
                .addScalar("jewelryName", StandardBasicTypes.STRING)
                .addScalar("price", StandardBasicTypes.DOUBLE)
                .addScalar("countryName", StandardBasicTypes.STRING)
                .addScalar("countryCode", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(CustomClass.class))
                .getResultList();
    }
}
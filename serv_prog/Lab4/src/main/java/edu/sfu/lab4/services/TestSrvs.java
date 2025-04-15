package edu.sfu.lab4.services;

import edu.sfu.lab4.manager.DAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class TestSrvs extends DAO {
    // Получение имени страны по ID
    public static String getName(int id) {
        Session session = getSession();
        Query<String> query = session.createQuery(
            "SELECT c.name FROM Country c WHERE c.id = :id", String.class
        );
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    // Получение списка имен в диапазоне ID
    public static List<String> getNamesInRange(int startId, int endId) {
        Session session = getSession();
        Query<String> query = session.createQuery(
            "SELECT c.name FROM Country c WHERE c.id BETWEEN :start AND :end", String.class
        );
        query.setParameter("start", startId);
        query.setParameter("end", endId);
        return query.list();
    }

    // Создание новой записи
    public static int createCountry(String name, String code) {
        begin();
        Session session = getSession();
        Query<?> query = session.createNativeQuery(
            "INSERT INTO Country (name, code) VALUES (:name, :code)"
        );
        query.setParameter("name", name);
        query.setParameter("code", code);
        int result = query.executeUpdate();
        commit();
        return result;
    }
}
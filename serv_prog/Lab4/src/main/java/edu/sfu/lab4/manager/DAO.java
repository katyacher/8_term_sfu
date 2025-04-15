package edu.sfu.lab4.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAO {
    protected static ThreadLocal<Session> session = new ThreadLocal<>();
    protected static SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .buildSessionFactory();

    public static Session getSession() {
        Session currentSession = session.get();
        if (currentSession == null) {
            currentSession = sessionFactory.openSession();
            session.set(currentSession);
        }
        return currentSession;
    }

    public static void begin() {
        getSession().beginTransaction();
    }

    public static void commit() {
        getSession().getTransaction().commit();
    }
}
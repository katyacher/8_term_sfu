package edu.sfu.lab5.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.sfu.lab5.model.*;

public class DAO {
    protected static ThreadLocal<Session> session = new ThreadLocal<>();
    protected static SessionFactory sessionFactory;
    
    static {
        try {
            sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(JewelryType.class)
                .addAnnotatedClass(Material.class)
                .addAnnotatedClass(Jewelry.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Order.class)
                .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    

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
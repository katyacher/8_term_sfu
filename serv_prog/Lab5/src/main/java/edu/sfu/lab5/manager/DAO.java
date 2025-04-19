package edu.sfu.lab5.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import edu.sfu.lab5.model.*;

public class DAO {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static Session currentSession;
    private static Transaction currentTransaction;

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
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
        if (currentSession == null || !currentSession.isOpen()) {
            currentSession = sessionFactory.openSession();
        }
        return currentSession;
    }

    public static void begin() {
        if (currentTransaction == null || !currentTransaction.isActive()) {
            currentTransaction = getSession().beginTransaction();
        }
    }

    public static void commit() {
        if (currentTransaction != null && currentTransaction.isActive()) {
            currentTransaction.commit();
        }
        closeSession();
    }

    public static void rollback() {
        if (currentTransaction != null && currentTransaction.isActive()) {
            currentTransaction.rollback();
        }
        closeSession();
    }

    public static void closeSession() {
        if (currentSession != null && currentSession.isOpen()) {
            currentSession.close();
        }
        currentSession = null;
        currentTransaction = null;
    }

    public static void shutdown() {
        closeSession();
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
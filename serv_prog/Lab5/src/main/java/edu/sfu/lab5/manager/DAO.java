package edu.sfu.lab5.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import edu.sfu.lab5.model.*;

public class DAO {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final ThreadLocal<Session> currentSession = new ThreadLocal<>();
    private static final ThreadLocal<Transaction> currentTransaction = new ThreadLocal<>();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
            return configuration.buildSessionFactory(builder.build());
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        Session session = currentSession.get();
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
            currentSession.set(session);
        }
        return session;
    }

    public static void begin() {
        Transaction transaction = currentTransaction.get();
        if (transaction == null) {
            transaction = getSession().beginTransaction();
            currentTransaction.set(transaction);
        }
    }

    public static void commit() {
        Transaction transaction = currentTransaction.get();
        if (transaction != null && transaction.isActive()) {
            transaction.commit();
            currentTransaction.remove();
            closeSession();
        }
    }

    public static void rollback() {
        Transaction transaction = currentTransaction.get();
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
            currentTransaction.remove();
            closeSession();
        }
    }

    public static void closeSession() {
        Session session = currentSession.get();
        if (session != null && session.isOpen()) {
            session.close();
            currentSession.remove();
        }
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}
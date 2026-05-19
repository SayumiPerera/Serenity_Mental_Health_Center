package lk.ijse.mental_health_therapy_center.config;

import lk.ijse.mental_health_therapy_center.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * HibernateUtil — Singleton SessionFactory.
 * Loads config ONLY from hibernate.properties (no hibernate.cfg.xml needed).
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    sessionFactory = buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // Load hibernate.properties manually — NO configure() call needed
            Properties props = new Properties();
            try (InputStream in = HibernateUtil.class
                    .getClassLoader().getResourceAsStream("hibernate.properties")) {
                if (in == null)
                    throw new RuntimeException("hibernate.properties not found in classpath!");
                props.load(in);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load hibernate.properties", e);
            }

            Configuration cfg = new Configuration();
            cfg.setProperties(props);

            // Register every entity class here
            cfg.addAnnotatedClass(User.class);
            cfg.addAnnotatedClass(Patient.class);
            cfg.addAnnotatedClass(Therapist.class);
            cfg.addAnnotatedClass(TherapyProgram.class);
            cfg.addAnnotatedClass(TherapySession.class);
            cfg.addAnnotatedClass(Payment.class);

            ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();

            return cfg.buildSessionFactory(sr);

        } catch (Exception e) {
            System.err.println("SessionFactory creation FAILED: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}

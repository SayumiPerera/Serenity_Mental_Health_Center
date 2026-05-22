package lk.ijse.mental_health_therapy_center.config;

import lk.ijse.mental_health_therapy_center.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;
    private static SessionFactory sessionFactory;

    private FactoryConfiguration() {

        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            properties.load(
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream("hibernate.properties")
            );

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Patient.class);
            configuration .addAnnotatedClass(Payment.class);
            configuration .addAnnotatedClass(Therapist.class);
            configuration .addAnnotatedClass(TherapyProgram.class);
            configuration .addAnnotatedClass(TherapySession.class);
            configuration  .addAnnotatedClass(Registration.class);
            configuration .addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load hibernate.properties", e);
        }
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null)
                ? factoryConfiguration = new FactoryConfiguration()
                : factoryConfiguration;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
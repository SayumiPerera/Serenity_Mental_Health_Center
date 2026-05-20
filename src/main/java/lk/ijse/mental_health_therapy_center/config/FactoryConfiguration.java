package lk.ijse.mental_health_therapy_center.config;

import com.mysql.cj.Session;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();//add configuration file
        Properties properties = new Properties();//add properties

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));//load properties file
        } catch (IOException e) {
            /*throw new RuntimeException(e);*/
            e.printStackTrace();
        }

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Users.class);
        configuration.addAnnotatedClass(Patients.class);
        configuration.addAnnotatedClass(TherapyProgram.class);
        configuration.addAnnotatedClass(Registration.class);
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(Therapist.class);
        configuration.addAnnotatedClass(TherapySession.class);
        configuration.addAnnotatedClass(TherapyDetail.class);
        sessionFactory = configuration.buildSessionFactory();

    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }

}


//import org.example.entity.Laptop;
//import org.example.entity.Student;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class FactoryConfiguration {
//    private static FactoryConfiguration factoryConfiguration;
//    private SessionFactory sessionFactory;
//    private FactoryConfiguration() {
//        Configuration configuration = new Configuration().configure()
//                .addAnnotatedClass(Student.class)
//                .addAnnotatedClass(Laptop.class);
//        sessionFactory = configuration.buildSessionFactory();
//    }
//    public static FactoryConfiguration getInstance() {
//        return (factoryConfiguration == null)
//                ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
//    }
//    public Session getSession() {
//        return sessionFactory.openSession();
//    }
//}
package lk.ijse.mental_health_therapy_center.dao.custom.impl;

import com.mysql.cj.Session;
import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center.dao.custom.PaymentDAO;
import lk.ijse.mental_health_therapy_center.entity.Payment;

import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Payment entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(entity);
            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Payment entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Payment payment = session.get(Payment.class, Integer.parseInt(id));
            if (payment != null) {
                session.remove(payment);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Payment> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            return session.createQuery(
                    "FROM Payment",
                    Payment.class
            ).list();

        } finally {
            session.close();
        }
    }

    @Override
    public Payment search(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            return session.get(Payment.class, Integer.parseInt(id));
        } finally {
            session.close();
        }
    }

    @Override
    public double getTotalPaymentsByPatient(int patientId) throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Double> query = session.createQuery(
                    "SELECT SUM(p.amount) FROM Payment p " +
                            "WHERE p.patient.id = :patientId",
                    Double.class
            );

            query.setParameter("patientId", patientId);
            Double total = query.uniqueResult();
            return total != null ? total : 0.0;
        } finally {
            session.close();
        }
    }

    @Override
    public int generateNextPaymentId() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Integer lastId = session.createQuery(
                            "SELECT MAX(p.id) FROM Payment p",
                            Integer.class
                    )
                    .uniqueResult();
            return lastId != null ? lastId + 1 : 1;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Payment> getPaymentsBySession(int sessionId) throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Payment> query = session.createQuery(
                    "FROM Payment p WHERE p.therapySession.id = :sessionId",
                    Payment.class
            );
            query.setParameter("sessionId", sessionId);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Payment> getPaymentsByPatient(int patientId) throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Payment> query = session.createQuery(
                    "FROM Payment p WHERE p.patient.id = :patientId",
                    Payment.class
            );
            query.setParameter("patientId", patientId);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public int getNextPaymentId() {
        Session session = factoryConfiguration.getSession();

        try {
            Integer lastId = session.createQuery(
                            "SELECT MAX(p.id) FROM Payment p",
                            Integer.class
                    )
                    .uniqueResult();
            return lastId != null ? lastId + 1 : 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean savePayment(Session session, Payment payment) {

        try {
            session.merge(payment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Object[]> getPendingPayments() {

        Session session = factoryConfiguration.getSession();
        try {
            String hql =
                    "SELECT p.patient.name, p.amount, p.date " +
                            "FROM Payment p " +
                            "ORDER BY p.date DESC";

            Query<Object[]> query =
                    session.createQuery(hql, Object[].class);
            return query.list();
        } finally {
            session.close();
        }
    }
}
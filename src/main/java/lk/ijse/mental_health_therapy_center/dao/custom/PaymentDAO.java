package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.config.HibernateUtil;
import lk.ijse.mental_health_therapy_center.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PaymentDAO — handles all DB operations for the Payment entity.
 */
public interface PaymentDAO {

    // ── SAVE
    public boolean save(Payment payment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(payment);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("PaymentDAO.save() failed: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE
    public boolean update(Payment payment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(payment);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("PaymentDAO.update() failed: " + e.getMessage());
            return false;
        }
    }

    // ── FIND BY ID
    public Optional<Payment> findById(int paymentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Payment.class, paymentId));
        } catch (Exception e) {
            System.err.println("PaymentDAO.findById() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FIND ALL
    public List<Payment> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Payment p ORDER BY p.paymentDate DESC", Payment.class).list();
        } catch (Exception e) {
            System.err.println("PaymentDAO.findAll() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── FIND BY PATIENT
    public List<Payment> findByPatient(int patientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Payment> query = session.createQuery("FROM Payment p WHERE p.patient.patientId = :pid " + "ORDER BY p.paymentDate DESC", Payment.class);
            query.setParameter("pid", patientId);
            return query.list();
        } catch (Exception e) {
            System.err.println("PaymentDAO.findByPatient() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── FIND PENDING PAYMENTS
    public List<Payment> findPending() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Payment p WHERE p.status = 'PENDING'", Payment.class).list();
        } catch (Exception e) {
            System.err.println("PaymentDAO.findPending() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── TOTAL REVENUE
    public double getTotalRevenue() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Double total = session.createQuery("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED'", Double.class).uniqueResult();
            return total != null ? total : 0.0;
        } catch (Exception e) {
            System.err.println("PaymentDAO.getTotalRevenue() failed: " + e.getMessage());
            return 0.0;
        }
    }
}

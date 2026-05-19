package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.config.HibernateUtil;
import lk.ijse.mental_health_therapy_center.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TherapistDAO — handles all DB operations for the Therapist entity.
 */
public interface TherapistDAO {

    // ── SAVE
    public boolean save(Therapist therapist) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(therapist);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapistDAO.save() failed: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE +
    public boolean update(Therapist therapist) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(therapist);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapistDAO.update() failed: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE
    public boolean delete(int therapistId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Therapist t = session.get(Therapist.class, therapistId);
            if (t != null) {
                session.delete(t);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapistDAO.delete() failed: " + e.getMessage());
            return false;
        }
    }

    // ── FIND BY ID
    public Optional<Therapist> findById(int therapistId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Therapist.class, therapistId));
        } catch (Exception e) {
            System.err.println("TherapistDAO.findById() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FIND ALL
    public List<Therapist> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Therapist", Therapist.class).list();
        } catch (Exception e) {
            System.err.println("TherapistDAO.findAll() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── FIND AVAILABLE ONLY
    public List<Therapist> findAvailable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Therapist t WHERE t.available = true", Therapist.class).list();
        } catch (Exception e) {
            System.err.println("TherapistDAO.findAvailable() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── FIND BY SPECIALIZATION
    public List<Therapist> findBySpecialization(String specialization) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Therapist> query = session.createQuery("FROM Therapist t WHERE LOWER(t.specialization) LIKE :spec", Therapist.class);
            query.setParameter("spec", "%" + specialization.toLowerCase() + "%");
            return query.list();
        } catch (Exception e) {
            System.err.println("TherapistDAO.findBySpecialization() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

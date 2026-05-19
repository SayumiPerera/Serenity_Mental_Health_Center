package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.config.HibernateUtil;
import lk.ijse.mental_health_therapy_center.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TherapySessionDAO — handles all DB operations for TherapySession entity.
 * Includes conflict checking for scheduling.
 */
public interface TherapySessionDAO {

    // ── SAVE
    public boolean save(TherapySession therapySession) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(therapySession);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapySessionDAO.save() failed: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE
    public boolean update(TherapySession therapySession) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(therapySession);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapySessionDAO.update() failed: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE
    public boolean delete(int sessionId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            TherapySession ts = session.get(TherapySession.class, sessionId);
            if (ts != null) {
                session.delete(ts);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapySessionDAO.delete() failed: " + e.getMessage());
            return false;
        }
    }

    // ── FIND BY ID
    public Optional<TherapySession> findById(int sessionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(TherapySession.class, sessionId));
        } catch (Exception e) {
            System.err.println("TherapySessionDAO.findById() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FIND ALL
    public List<TherapySession> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM TherapySession ts ORDER BY ts.sessionDate DESC", TherapySession.class).list();
        } catch (Exception e) {
            System.err.println("TherapySessionDAO.findAll() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── FIND BY PATIENT
    public List<TherapySession> findByPatient(int patientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<TherapySession> query = session.createQuery("FROM TherapySession ts WHERE ts.patient.patientId = :pid " + "ORDER BY ts.sessionDate DESC", TherapySession.class);
            query.setParameter("pid", patientId);
            return query.list();
        } catch (Exception e) {
            System.err.println("TherapySessionDAO.findByPatient() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── FIND BY THERAPIST
    public List<TherapySession> findByTherapist(int therapistId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<TherapySession> query = session.createQuery("FROM TherapySession ts WHERE ts.therapist.therapistId = :tid " + "ORDER BY ts.sessionDate DESC", TherapySession.class);
            query.setParameter("tid", therapistId);
            return query.list();
        } catch (Exception e) {
            System.err.println("TherapySessionDAO.findByTherapist() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── CHECK THERAPIST CONFLICT (scheduling)
    // Returns true if therapist already has a session within 1 hour of requested time
    public boolean hasConflict(int therapistId, LocalDateTime requestedTime) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime from = requestedTime.minusHours(1);
            LocalDateTime to   = requestedTime.plusHours(1);
            Query<Long> query  = session.createQuery("SELECT COUNT(ts) FROM TherapySession ts " + "WHERE ts.therapist.therapistId = :tid " + "AND ts.sessionDate BETWEEN :from AND :to " + "AND ts.status != 'CANCELLED'", Long.class);
            query.setParameter("tid",  therapistId);
            query.setParameter("from", from);
            query.setParameter("to",   to);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            System.err.println("TherapySessionDAO.hasConflict() failed: " + e.getMessage());
            return false;
        }
    }
}
package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.config.HibernateUtil;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TherapyProgramDAO — handles all DB operations for TherapyProgram entity.
 */
public interface TherapyProgramDAO {

    // ── SAVE
    public boolean save(TherapyProgram program) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(program);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapyProgramDAO.save() failed: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE
    public boolean update(TherapyProgram program) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(program);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapyProgramDAO.update() failed: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE
    public boolean delete(int programId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            TherapyProgram p = session.get(TherapyProgram.class, programId);
            if (p != null) {
                session.delete(p);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("TherapyProgramDAO.delete() failed: " + e.getMessage());
            return false;
        }
    }

    // ── FIND BY ID
    public Optional<TherapyProgram> findById(int programId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(TherapyProgram.class, programId));
        } catch (Exception e) {
            System.err.println("TherapyProgramDAO.findById() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FIND BY CODE (e.g. MT1001)
    public Optional<TherapyProgram> findByCode(String code) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<TherapyProgram> query = session.createQuery("FROM TherapyProgram tp WHERE tp.programCode = :code", TherapyProgram.class);
            query.setParameter("code", code);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            System.err.println("TherapyProgramDAO.findByCode() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FIND ALL
    public List<TherapyProgram> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM TherapyProgram", TherapyProgram.class).list();
        } catch (Exception e) {
            System.err.println("TherapyProgramDAO.findAll() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── COUNT ALL PROGRAMS
    // Used in the "enrolled in all programs" HQL query
    public long countAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(tp) FROM TherapyProgram tp", Long.class).uniqueResult();
        } catch (Exception e) {
            return 0;
        }
    }
}

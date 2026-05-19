package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.config.HibernateUtil;
import lk.ijse.mental_health_therapy_center.entity.Patient;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * PatientDAO — handles all DB operations for the Patient entity.
 * Includes fetching patients with their enrolled therapy programs.
 */
public interface PatientDAO {

    // ── SAVE
    public boolean save(Patient patient) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(patient);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("PatientDAO.save() failed: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE
    public boolean update(Patient patient) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(patient);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("PatientDAO.update() failed: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE
    public boolean delete(int patientId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Patient patient = session.get(Patient.class, patientId);
            if (patient != null) {
                session.delete(patient);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("PatientDAO.delete() failed: " + e.getMessage());
            return false;
        }
    }

    // ── FIND BY ID
    public Optional<Patient> findById(int patientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Patient.class, patientId));
        } catch (Exception e) {
            System.err.println("PatientDAO.findById() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FIND ALL
    public List<Patient> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Patient", Patient.class).list();
        } catch (Exception e) {
            System.err.println("PatientDAO.findAll() failed: " + e.getMessage());
            return List.of();
        }
    }

    // ── FIND BY EMAIL
    public Optional<Patient> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Patient> query = session.createQuery("FROM Patient p WHERE p.email = :email", Patient.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            System.err.println("PatientDAO.findByEmail() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FETCH PATIENTS WITH ENROLLED PROGRAMS (HQL JOIN FETCH) ─
    // This is the required HQL query from the assignment (Part A - Task 4)
    // JOIN FETCH loads enrolled programs in the same query (avoids lazy loading issues)
    public List<Patient> findAllWithPrograms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT p FROM Patient p " + "LEFT JOIN FETCH p.enrolledPrograms", Patient.class).list();
        } catch (Exception e) {
            System.err.println("PatientDAO.findAllWithPrograms() failed: " + e.getMessage());
            return List.of();
        }
    }

    // ── PATIENTS ENROLLED IN ALL THERAPY PROGRAMS (Part A - Task 2) ─
    // HQL subquery: finds patients who have enrolled in every available program
    public List<Patient> findPatientsEnrolledInAllPrograms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p FROM Patient p " + "WHERE (SELECT COUNT(DISTINCT tp.programId) FROM TherapyProgram tp) = " + "      (SELECT COUNT(DISTINCT ep.programId) FROM Patient p2 " + "       JOIN p2.enrolledPrograms ep WHERE p2.patientId = p.patientId)";
            return session.createQuery(hql, Patient.class).list();
        } catch (Exception e) {
            System.err.println("PatientDAO.findPatientsEnrolledInAllPrograms() failed: " + e.getMessage());
            return List.of();
        }
    }

    // ── ENROLL PATIENT IN A PROGRAM
    public boolean enrollInProgram(int patientId, TherapyProgram program) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Patient patient = session.get(Patient.class, patientId);
            if (patient != null && !patient.getEnrolledPrograms().contains(program)) {
                patient.getEnrolledPrograms().add(program);
                session.update(patient);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("PatientDAO.enrollInProgram() failed: " + e.getMessage());
            return false;
        }
    }

    // ── SEARCH BY NAME
    public List<Patient> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Patient> query = session.createQuery("FROM Patient p WHERE LOWER(p.firstName) LIKE :kw OR LOWER(p.lastName) LIKE :kw", Patient.class);
            query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
            return query.list();
        } catch (Exception e) {
            System.err.println("PatientDAO.searchByName() failed: " + e.getMessage());
            return List.of();
        }
    }
}

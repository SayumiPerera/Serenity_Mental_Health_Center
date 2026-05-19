package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.config.HibernateUtil;
import lk.ijse.mental_health_therapy_center.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserDAO — handles all DB operations for the User entity.
 * Used for login, registration, and credential updates.
 */
public interface UserDAO {

    // ── SAVE
    public boolean save(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("UserDAO.save() failed: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE
    public boolean update(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("UserDAO.update() failed: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE
    public boolean delete(int userId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("UserDAO.delete() failed: " + e.getMessage());
            return false;
        }
    }

    // ── FIND BY ID
    public Optional<User> findById(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, userId));
        } catch (Exception e) {
            System.err.println("UserDAO.findById() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FIND BY USERNAME (for login)
    public Optional<User> findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            System.err.println("UserDAO.findByUsername() failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ── FIND ALL
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            System.err.println("UserDAO.findAll() failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ── USERNAME EXISTS CHECK
    public boolean usernameExists(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class);
            query.setParameter("username", username);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}

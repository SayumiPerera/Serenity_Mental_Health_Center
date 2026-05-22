package lk.ijse.mental_health_therapy_center.dao.custom.impl;

import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center.dao.custom.UserDAO;
import lk.ijse.mental_health_therapy_center.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findByUsername(String username) throws Exception {

        Session session = FactoryConfiguration.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hql = "FROM User u WHERE u.username = :username";

            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);

            List<User> results = query.list();

            transaction.commit();

            return results.isEmpty() ? null : results.get(0);

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw e;

        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(User entity) throws Exception {

        Session session = FactoryConfiguration.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(entity);

            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw e;

        } finally {
            session.close();
        }
    }

    @Override
    public boolean add(User entity) throws Exception {
        return save(entity);
    }

    @Override
    public boolean update(User entity) throws Exception {

        Session session = FactoryConfiguration.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.update(entity);

            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw e;

        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws Exception {

        Session session = FactoryConfiguration.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);

            if (user != null) {
                session.delete(user);
            }

            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw e;

        } finally {
            session.close();
        }
    }

    @Override
    public User search(String id) throws Exception {

        Session session = FactoryConfiguration.getSession();

        try {
            return session.get(User.class, id);

        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() throws Exception {

        Session session = FactoryConfiguration.getSession();

        try {

            String hql = "FROM User";

            Query<User> query = session.createQuery(hql, User.class);

            return query.list();

        } finally {
            session.close();
        }
    }
}
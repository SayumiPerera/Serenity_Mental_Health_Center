package lk.ijse.mental_health_therapy_center.dao.custom.impl;

import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapySessionDAO;
import lk.ijse.mental_health_therapy_center.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {

    @Override
    public boolean save(TherapySession entity) {

        Session session = (Session) FactoryConfiguration.getInstance();
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
    public boolean add(TherapySession entity) throws Exception {
        return save(entity);
    }

    @Override
    public boolean update(TherapySession entity) {

        Session session = FactoryConfiguration.getSession();
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
    public boolean delete(String id) throws Exception {

        return delete(Integer.parseInt(id));
    }

    @Override
    public TherapySession search(String id) throws Exception {

        return search(Integer.parseInt(id));
    }

    @Override
    public boolean delete(int sessionId) {

        Session session = FactoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {

            TherapySession therapySession =
                    session.get(TherapySession.class, sessionId);

            if (therapySession != null) {

                session.remove(therapySession);

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
    public boolean delete(Integer sessionId) {
        return false;
    }

    @Override
    public TherapySession search(int sessionId) {

        Session session = FactoryConfiguration.getSession();

        try {

            return session.get(TherapySession.class, sessionId);

        } finally {
            session.close();
        }
    }

    @Override
    public List<TherapySession> getAll() {

        Session session = FactoryConfiguration.getSession();

        try {

            return session.createQuery(
                    "FROM TherapySession",
                    TherapySession.class
            ).list();

        } finally {
            session.close();
        }
    }

    @Override
    public String getNextId() {

        Session session = FactoryConfiguration.getSession();

        try {

            Integer lastId = session.createQuery(
                    "SELECT MAX(t.sessionId) FROM TherapySession t",
                    Integer.class
            ).uniqueResult();

            if (lastId == null) {
                return "1";
            }

            return String.valueOf(lastId + 1);

        } finally {
            session.close();
        }
    }
}
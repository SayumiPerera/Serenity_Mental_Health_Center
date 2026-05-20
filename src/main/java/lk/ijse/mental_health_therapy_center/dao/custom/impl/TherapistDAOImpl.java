package lk.ijse.mental_health_therapy_center.dao.custom.impl;

import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapistDAO;
import lk.ijse.mental_health_therapy_center.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapistDAOImpl implements TherapistDAO {

    private final FactoryConfiguration factoryConfiguration =
            FactoryConfiguration.getInstance();

    @Override
    public boolean save(Therapist entity) {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(entity);

            transaction.commit();
            return true;

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Therapist entity) {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {

            session.merge(entity);

            transaction.commit();
            return true;

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();
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

            Therapist therapist =
                    session.get(Therapist.class, Integer.parseInt(id));

            if (therapist != null) {

                session.remove(therapist);

                transaction.commit();
                return true;
            }

            return false;

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public List<Therapist> getAll() {

        Session session = factoryConfiguration.getSession();

        try {

            return session.createQuery(
                    "FROM Therapist",
                    Therapist.class
            ).getResultList();

        } finally {
            session.close();
        }
    }

    @Override
    public Therapist search(String id) {

        Session session = factoryConfiguration.getSession();

        try {

            return session.get(
                    Therapist.class,
                    Integer.parseInt(id)
            );

        } finally {
            session.close();
        }
    }

    @Override
    public String getNextId() {

        Session session = factoryConfiguration.getSession();

        try {

            Integer lastId = session.createQuery(
                    "SELECT MAX(t.id) FROM Therapist t",
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

    @Override
    public ArrayList<String> getAllTherapistNames() {

        Session session = factoryConfiguration.getSession();

        ArrayList<String> therapistNames = new ArrayList<>();

        try {

            List<String> names = session.createQuery(
                    "SELECT t.name FROM Therapist t",
                    String.class
            ).getResultList();

            therapistNames.addAll(names);

        } finally {
            session.close();
        }

        return therapistNames;
    }

    @Override
    public String getTherapistNameById(int therapistId) {

        Session session = factoryConfiguration.getSession();

        try {

            Therapist therapist =
                    session.get(Therapist.class, therapistId);

            if (therapist != null) {
                return therapist.getName();
            }

            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public String getTherapistIdByName(String therapistName) {

        Session session = factoryConfiguration.getSession();

        try {

            Integer id = session.createQuery(
                            "SELECT t.id FROM Therapist t WHERE t.name = :name",
                            Integer.class
                    )
                    .setParameter("name", therapistName)
                    .uniqueResult();

            return id != null ? String.valueOf(id) : null;

        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Therapist> findByPK(int therapistId) {

        Session session = factoryConfiguration.getSession();

        try {

            Therapist therapist =
                    session.get(Therapist.class, therapistId);

            return Optional.ofNullable(therapist);

        } finally {
            session.close();
        }
    }
}
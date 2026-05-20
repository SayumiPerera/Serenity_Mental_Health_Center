package lk.ijse.mental_health_therapy_center.dao.custom.impl;

import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {

    private final FactoryConfiguration factoryConfiguration =
            FactoryConfiguration.getInstance();

    @Override
    public boolean save(TherapyProgram entity) {

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
    public boolean update(TherapyProgram entity) {

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

            TherapyProgram therapyProgram =
                    session.get(TherapyProgram.class, Integer.parseInt(id));

            if (therapyProgram != null) {

                session.remove(therapyProgram);

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
    public List<TherapyProgram> getAll() {

        Session session = factoryConfiguration.getSession();

        try {

            return session.createQuery(
                    "FROM TherapyProgram",
                    TherapyProgram.class
            ).getResultList();

        } finally {
            session.close();
        }
    }

    @Override
    public TherapyProgram search(String id) {

        Session session = factoryConfiguration.getSession();

        try {

            return session.get(
                    TherapyProgram.class,
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
                    "SELECT MAX(t.id) FROM TherapyProgram t",
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
    public ArrayList<String> getAllProgramNames() {

        Session session = factoryConfiguration.getSession();

        ArrayList<String> programNames = new ArrayList<>();

        try {

            List<String> names = session.createQuery(
                    "SELECT t.name FROM TherapyProgram t",
                    String.class
            ).getResultList();

            programNames.addAll(names);

        } finally {
            session.close();
        }

        return programNames;
    }

    @Override
    public String getProgramNameById(int programId) {

        Session session = factoryConfiguration.getSession();

        try {

            TherapyProgram therapyProgram =
                    session.get(TherapyProgram.class, programId);

            if (therapyProgram != null) {
                return therapyProgram.getName();
            }

            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public String getProgramIdByName(String programName) {

        Session session = factoryConfiguration.getSession();

        try {

            Integer id = session.createQuery(
                            "SELECT t.id FROM TherapyProgram t WHERE t.name = :name",
                            Integer.class
                    )
                    .setParameter("name", programName)
                    .uniqueResult();

            return id != null ? String.valueOf(id) : null;

        } finally {
            session.close();
        }
    }

    @Override
    public double getProgramFeeById(int programId) {

        Session session = factoryConfiguration.getSession();

        try {

            Double fee = session.createQuery(
                            "SELECT t.fee FROM TherapyProgram t WHERE t.id = :id",
                            Double.class
                    )
                    .setParameter("id", programId)
                    .uniqueResult();

            return fee != null ? fee : 0.0;

        } finally {
            session.close();
        }
    }

    @Override
    public Optional<TherapyProgram> findByPK(int programId) {

        Session session = factoryConfiguration.getSession();

        try {

            TherapyProgram therapyProgram =
                    session.get(TherapyProgram.class, programId);

            return Optional.ofNullable(therapyProgram);

        } finally {
            session.close();
        }
    }
}
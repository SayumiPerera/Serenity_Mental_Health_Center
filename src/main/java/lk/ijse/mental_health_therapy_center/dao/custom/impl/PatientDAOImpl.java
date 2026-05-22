package lk.ijse.mental_health_therapy_center.dao.custom.impl;

import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center.dao.custom.PatientDAO;
import lk.ijse.mental_health_therapy_center.entity.Patient;
import lk.ijse.mental_health_therapy_center.exception.RegistrationException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PatientDAOImpl implements PatientDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Patient entity) {

        Session session = (Session) factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Patient existsPatient = session.get(Patient.class, entity.getId());

            if (existsPatient != null) {
                throw new RegistrationException("Patient already exists");
            }

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
    public boolean add(Patient entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Patient entity) {

        Session session = (Session) factoryConfiguration.getSession();
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
        return false;
    }

    @Override
    public Patient search(String id) throws Exception {
        return null;
    }

    @Override
    public boolean delete(int id) {

        Session session = (Session) factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Patient patient = session.get(Patient.class, id);
            if (patient == null) {
                throw new RegistrationException("Patient not found");
            }
            session.remove(patient);
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
    public List<Patient> getAll() {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            return session.createQuery(
                    "FROM Patient",
                    Patient.class
            ).getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Patient> searchPatient(String searchText) {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            return session.createQuery(
                            "FROM Patient p WHERE " +
                                    "CAST(p.id AS string) LIKE :text OR " +
                                    "p.name LIKE :text OR " +
                                    "p.nic LIKE :text OR " +
                                    "CAST(p.age AS string) LIKE :text OR " +
                                    "p.gender LIKE :text OR " +
                                    "p.phone LIKE :text OR " +
                                    "p.email LIKE :text",
                            Patient.class
                    )
                    .setParameter("text", "%" + searchText + "%")
                    .getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public String getNextId() {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            Integer lastId = session.createQuery(
                            "SELECT MAX(p.id) FROM Patient p",
                            Integer.class
                    )
                    .uniqueResult();
            if (lastId == null) {
                return "1";
            }
            return String.valueOf(lastId + 1);
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getPatientIdAndNames() {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            List<Object[]> results = session.createQuery(
                    "SELECT p.id, p.name FROM Patient p"
            ).getResultList();
            List<String> list = new ArrayList<>();
            for (Object[] result : results) {
                list.add(result[0] + " - " + result[1]);
            }

            return list;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<String> getAllPatientNames() {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            List<String> names = session.createQuery(
                    "SELECT p.name FROM Patient p",
                    String.class
            ).getResultList();
            return new ArrayList<>(names);
        } finally {
            session.close();
        }
    }

    @Override
    public String getPatientNameById(int patientId) {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            Patient patient = session.get(Patient.class, patientId);
            if (patient != null) {
                return patient.getName();
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public int getPatientIdByName(String patientName) {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            Integer id = session.createQuery(
                            "SELECT p.id FROM Patient p WHERE p.name = :name",
                            Integer.class
                    )
                    .setParameter("name", patientName)
                    .uniqueResult();
            return id != null ? id : 0;
        } finally {
            session.close();
        }
    }

    @Override
    public Patient getPatientById(int patientId) {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            return session.get(Patient.class, patientId);

        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Patient> findByPK(int patientId) {

        Session session = (Session) factoryConfiguration.getSession();
        try {
            Patient patient = session.get(Patient.class, patientId);
            return Optional.ofNullable(patient);
        } finally {
            session.close();
        }
    }

    @Override
    public long getTotalPatientCount() {

        Session session = (Session) factoryConfiguration.getSession();

        try {
            Long count = session.createQuery(
                    "SELECT COUNT(p.id) FROM Patient p",
                    Long.class
            ).uniqueResult();
            return count != null ? count : 0;
        } finally {
            session.close();
        }
    }

    @Override
    public Map<String, Long> getPatientCountByGender() {

        Session session = (Session) factoryConfiguration.getSession();
        try {
            List<Object[]> results = session.createQuery(
                    "SELECT p.gender, COUNT(p.id) " +
                            "FROM Patient p GROUP BY p.gender"
            ).getResultList();

            Map<String, Long> genderMap = new HashMap<>();

            for (Object[] result : results) {
                genderMap.put(
                        (String) result[0],
                        (Long) result[1]
                );
            }
            return genderMap;

        } finally {
            session.close();
        }
    }

    @Override
    public Patient search(int patientId) {
        return null;
    }
}
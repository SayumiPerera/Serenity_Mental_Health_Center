package lk.ijse.mental_health_therapy_center.dao;

import lk.ijse.mental_health_therapy_center.dao.custom.impl.*;

/**
 * Factory that returns the correct DAO implementation for a given DAOTypes enum.
 *
 * Uses traditional switch-case (Java 11 safe).
 */
public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes {
        LOGIN,
        USER,
        PATIENT,
        THERAPIST,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        REGISTRATION,
        PAYMENT,
        QUERY
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case LOGIN:
                return (T) new LoginDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            case PATIENT:
                return (T) new PatientDAOImpl();
            case THERAPIST:
                return (T) new TherapistDAOImpl();
            case THERAPY_PROGRAM:
                return (T) new TherapyProgramDAOImpl();
            case THERAPY_SESSION:
                return (T) new TherapySessionDAOImpl();
            case REGISTRATION:
                return (T) new RegistrationDAOImpl();
            case PAYMENT:
                return (T) new PaymentDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}
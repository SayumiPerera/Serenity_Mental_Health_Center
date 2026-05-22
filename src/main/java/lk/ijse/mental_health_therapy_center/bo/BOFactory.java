package lk.ijse.mental_health_therapy_center.bo;

import lk.ijse.mental_health_therapy_center.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public static BOFactory getInstance() {
        return getBoFactory();
    }

    public enum BOTypes {
        LOGIN,
        USER,
        PATIENT,
        THERAPIST,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        REGISTRATION,
        PAYMENT
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperBO> T getBO(BOTypes boTypes) {
        switch (boTypes) {
            case LOGIN:
                return (T) new LoginBOImpl();
            case USER:
                return (T) new UserBOImpl();
            case PATIENT:
                return (T) new PatientBOImpl();
            case THERAPIST:
                return (T) new TherapistBOImpl();
            case THERAPY_PROGRAM:
                return (T) new TherapyProgramBOImpl();
            case THERAPY_SESSION:
                return (T) new TherapySessionBOImpl();
            case REGISTRATION:
                return (T) new RegistrationBOImpl();
            case PAYMENT:
                return (T) new PaymentBOImpl();
            default:
                return null;
        }
    }
}
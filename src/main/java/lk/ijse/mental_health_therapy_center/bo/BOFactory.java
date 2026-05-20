package lk.ijse.mental_health_therapy_center.bo;

import lk.ijse.mental_health_therapy_center.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return (boFactory== null) ? boFactory
                = new BOFactory() : boFactory;
    }
    public enum BOTypes{
        LOGIN,
        PATIENT,
        PAYMENT,
        THERAPIST,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        REGISTRATION,
        USER
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperBO >T getBO(BOTypes boTypes){
        switch(boTypes){
            case LOGIN -> {
                return (T) new LoginBOImpl();
            }
            case PAYMENT -> {
                return (T) new PaymentBOImpl();
            }
            case PATIENT -> {
                return (T) new PatientBOImpl();
            }
            case THERAPIST -> {
                return (T) new TherapistBOImpl();
            }
            case THERAPY_PROGRAM -> {
                return (T) new TherapyProgramBOImpl();
            }
            case THERAPY_SESSION -> {
                return (T) new TherapySessionBOImpl();
            }
            case REGISTRATION -> {
                return (T) new RegistrationBOImpl();
            }
            case USER -> {
                return (T) new UserBOImpl();
            }
            default -> {
                return null;
            }
        }
    }
}

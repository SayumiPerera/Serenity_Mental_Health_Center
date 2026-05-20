package lk.ijse.mental_health_therapy_center.exception;

public class TherapySessionException extends RuntimeException {
    public TherapySessionException(String message) {
        super("Therapy Session Exception: " + message);
    }
}




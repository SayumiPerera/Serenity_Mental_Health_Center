package lk.ijse.mental_health_therapy_center.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super("Registration failed: " + message);
    }
}

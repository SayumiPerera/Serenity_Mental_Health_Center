package lk.ijse.mental_health_therapy_center.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super("Login Exception: " + message);
    }
}
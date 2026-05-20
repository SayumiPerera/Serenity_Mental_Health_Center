package lk.ijse.mental_health_therapy_center.exception;


public class PaymentException extends RuntimeException  {
    public PaymentException(String message) {
        super("Payment Exception: " + message);
    }
}

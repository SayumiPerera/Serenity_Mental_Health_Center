package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center.entity.Payment;
import org.hibernate.Session;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {

    double getTotalPaymentsByPatient(int patientId) throws Exception;
    int generateNextPaymentId() throws Exception;
    List<Payment> getPaymentsBySession(int sessionId) throws Exception;
    List<Payment> getPaymentsByPatient(int patientId) throws Exception;
    int getNextPaymentId();
    boolean savePayment(Session session, Payment payment);
    List<Object[]> getPendingPayments();
}
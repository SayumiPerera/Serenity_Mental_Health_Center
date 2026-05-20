package lk.ijse.mental_health_therapy_center.bo.custom;

import lk.ijse.mental_health_therapy_center.bo.SuperBO;
import lk.ijse.mental_health_therapy_center.dto.PaymentDTO;

import java.util.List;
import java.util.Map;

public interface PaymentBO extends SuperBO {

    boolean savePayment(PaymentDTO paymentDTO, int patientId, int programId) throws Exception;
    List<PaymentDTO> getAllPayments();
    double getTotalPaymentsByPatient(int patientId) throws Exception;
    int generateNextPaymentId() throws Exception;
    List<PaymentDTO> getPaymentsBySession(int sessionId) throws Exception;
    List<PaymentDTO> getPaymentsByPatientAndProgram(int patientId, int programId);
    int getNextPaymentId();
    Map<String, Double> getMonthlyRevenue();
    List<Object[]> getPendingPayments();
}
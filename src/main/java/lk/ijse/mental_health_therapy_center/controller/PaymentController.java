package lk.ijse.mental_health_therapy_center.controller;

import lk.ijse.mental_health_therapy_center.entity.Payment;
import lk.ijse.mental_health_therapy_center.exception.PaymentException;

import java.util.List;

/**
 * PaymentController — Façade between Payment UI and PaymentService.
 * Handles payment processing, refunds, invoice generation, and reporting.
 */
public class PaymentController {

    private final PaymentBO paymentBO = new PaymentBO();

    /**
     * Process a payment for a patient enrolling in a therapy program.
     * Called from PaymentDialog when the "Process Payment" button is clicked.
     *
     * @param patientId  the patient making the payment
     * @param programId  the therapy program being paid for
     * @param method     CASH, CARD, or BANK_TRANSFER
     * @return the saved Payment entity
     * @throws PaymentException if patient/program not found or amount is invalid
     */
    public Payment processPayment(int patientId, int programId, Payment.PaymentMethod method) throws PaymentException {
        return paymentBO.processPayment(patientId, programId, method);
    }

    /**
     * Refund a payment.
     * Sets the payment status to REFUNDED.
     */
    public boolean refundPayment(int paymentId) throws PaymentException {
        return paymentBO.refundPayment(paymentId);
    }

    /**
     * Generate a plain-text invoice string for a payment.
     * Displayed in a popup dialog when "Print Invoice" is clicked.
     */
    public String generateInvoice(int paymentId) throws PaymentException {
        return paymentBO.generateInvoice(paymentId);
    }

    /** Load all payments for the payment table. */
    public List<Payment> getAllPayments() {
        return paymentBO.getAllPayments();
    }

    /** Load payments for a specific patient. Used in Patient History tab. */
    public List<Payment> getPaymentsByPatient(int patientId) {
        return paymentBO.getPaymentsByPatient(patientId);
    }

    /** Load all pending payments. Used in Receptionist Reports. */
    public List<Payment> getPendingPayments() {
        return paymentBO.getPendingPayments();
    }

    /**
     * Get the total revenue from all COMPLETED payments.
     * Displayed in the payment table header and Admin reports.
     */
    public double getTotalRevenue() {
        return paymentBO.getTotalRevenue();
    }
}

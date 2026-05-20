package lk.ijse.mental_health_therapy_center.bo.custom.impl;

import lk.ijse.mental_health_therapy_center.bo.custom.PaymentBO;
import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.dao.custom.PaymentDAO;
import lk.ijse.mental_health_therapy_center.dto.PaymentDTO;
import lk.ijse.mental_health_therapy_center.entity.Patient;
import lk.ijse.mental_health_therapy_center.entity.Payment;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;
import lk.ijse.mental_health_therapy_center.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean savePayment(
            PaymentDTO paymentDTO,
            int patientId,
            int programId
    ) throws Exception {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Patient patient = session.get(Patient.class, patientId);
            TherapyProgram program = session.get(TherapyProgram.class, programId);
            TherapySession therapySession = session.get(TherapySession.class, paymentDTO.getSessionId());

            Payment payment = new Payment();

            payment.setId(paymentDTO.getId());
            payment.setPatientName(paymentDTO.getPatientName());
            payment.setDate(paymentDTO.getDate());
            payment.setAmount(paymentDTO.getAmount());

            payment.setPatient(patient);
            payment.setProgram(program);
            payment.setTherapySession(therapySession);

            boolean isSaved = paymentDAO.savePayment(session, payment);
            transaction.commit();
            return isSaved;

        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        for (Payment payment : payments) {
            paymentDTOS.add(
                    new PaymentDTO(
                            payment.getId(),
                            payment.getTherapySession().getId(),
                            payment.getPatientName(),
                            payment.getProgram().getId(),
                            payment.getDate(),
                            payment.getAmount()
                    )
            );
        }
        return paymentDTOS;
    }

    @Override
    public double getTotalPaymentsByPatient(int patientId) throws Exception {
        return paymentDAO.getTotalPaymentsByPatient(patientId);
    }

    @Override
    public int generateNextPaymentId() throws Exception {
        return paymentDAO.generateNextPaymentId();
    }

    @Override
    public List<PaymentDTO> getPaymentsBySession(int sessionId) throws Exception {
        List<Payment> payments = paymentDAO.getPaymentsBySession(sessionId);
        List<PaymentDTO> dtoList = new ArrayList<>();

        for (Payment payment : payments) {
            dtoList.add(new PaymentDTO(
                            payment.getId(),
                            payment.getTherapySession().getId(),
                            payment.getPatientName(),
                            payment.getProgram().getId(),
                            payment.getDate(),
                            payment.getAmount()
                    )
            );
        }
        return dtoList;
    }

    @Override
    public List<PaymentDTO> getPaymentsByPatientAndProgram(
            int patientId,
            int programId
    ) {

        List<PaymentDTO> dtoList = new ArrayList<>();

        try {
            List<Payment> payments = paymentDAO.getPaymentsByPatient(patientId);

            for (Payment payment : payments) {
                if (payment.getProgram().getId() == programId) {

                    dtoList.add(
                            new PaymentDTO(
                                    payment.getId(),
                                    payment.getTherapySession().getId(),
                                    payment.getPatientName(),
                                    payment.getProgram().getId(),
                                    payment.getDate(),
                                    payment.getAmount()
                            )
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dtoList;
    }

    @Override
    public int getNextPaymentId() {
        return paymentDAO.getNextPaymentId();
    }

    @Override
    public Map<String, Double> getMonthlyRevenue() {
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            String hql =
                    "SELECT FUNCTION('DATE_FORMAT', p.date, '%Y-%m'), " +
                            "SUM(p.amount) " +
                            "FROM Payment p " +
                            "GROUP BY FUNCTION('DATE_FORMAT', p.date, '%Y-%m')";

            Query<Object[]> query = session.createQuery(hql, Object[].class);

            List<Object[]> results = query.getResultList();

            for (Object[] result : results) {
                String month = (String) result[0];
                Double total = (Double) result[1];
                monthlyRevenue.put(month, total);
            }

            transaction.commit();

            LocalDate now = LocalDate.now();

            for (int i = 11; i >= 0; i--) {
                LocalDate month = now.minusMonths(i);
                String monthKey = month.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                monthlyRevenue.putIfAbsent(monthKey, 0.0);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return monthlyRevenue;
    }
    @Override
    public List<Object[]> getPendingPayments() {
        return paymentDAO.getPendingPayments();
    }
}
package lk.ijse.mental_health_therapy_center.entity;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    // Many Payments → One Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Many Payments → One TherapyProgram
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;

    public enum Status {
        PENDING,
        COMPLETED,
        REFUNDED
    }
    public enum PaymentMethod {
        CASH,
        CARD,
        BANK_TRANSFER
    }

    public Payment() {}
    public Payment(double amount, Patient patient, TherapyProgram therapyProgram, PaymentMethod paymentMethod) {
        this.amount = amount;
        this.patient = patient;
        this.therapyProgram = therapyProgram;
        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDate.now();
        this.status = Status.COMPLETED;
    }

    public int getPaymentId(){
        return paymentId;
    }
    public double getAmount(){
        return amount;
    }
    public void setAmount(double a){
        this.amount = a;
    }
    public LocalDate getPaymentDate(){
        return paymentDate;
    }
    public void setPaymentDate(LocalDate d){
        this.paymentDate = d;
    }
    public Status getStatus(){
        return status;
    }
    public void setStatus(Status s){
        this.status = s;
    }
    public PaymentMethod getPaymentMethod(){
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod m){
        this.paymentMethod = m;
    }
    public Patient getPatient(){
        return patient;
    }
    public void setPatient(Patient p){
        this.patient = p;
    }
    public TherapyProgram getTherapyProgram(){
        return therapyProgram;
    }
    public void setTherapyProgram(TherapyProgram p) {
        this.therapyProgram = p;
    }
}

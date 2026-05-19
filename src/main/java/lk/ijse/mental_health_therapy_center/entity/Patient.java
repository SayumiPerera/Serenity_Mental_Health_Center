package lk.ijse.mental_health_therapy_center.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "medical_history", length = 1000)
    private String medicalHistory;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    // Many-to-Many: Patient <-> TherapyProgram (join table: patient_programs)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "patient_programs",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private List<TherapyProgram> enrolledPrograms = new ArrayList<>();

    // One Patient → Many TherapySessions
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions = new ArrayList<>();

    // One Patient → Many Payments
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    public Patient() {}
    public Patient(String firstName, String lastName, String email, String phone, LocalDate dateOfBirth, String address, String medicalHistory) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.medicalHistory = medicalHistory;
        this.registrationDate = LocalDate.now();
    }

    public int getPatientId() {
        return patientId;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String n) {
        this.firstName = n;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String n) {
        this.lastName = n;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String e) {
        this.email = e;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String p) {
        this.phone = p;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate d){
        this.dateOfBirth = d;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String a) {
        this.address = a;
    }
    public String getMedicalHistory(){
        return medicalHistory;
    }
    public void setMedicalHistory(String m){
        this.medicalHistory = m;
    }
    public LocalDate getRegistrationDate(){
        return registrationDate;
    }
    public void setRegistrationDate(LocalDate d) {
        this.registrationDate = d;
    }
    public List<TherapyProgram> getEnrolledPrograms(){
        return enrolledPrograms;
    }
    public void setEnrolledPrograms(List<TherapyProgram> p){
        this.enrolledPrograms = p;
    }
    public List<TherapySession> getTherapySessions() {
        return therapySessions;
    }
    public List<Payment> getPayments(){
        return payments;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

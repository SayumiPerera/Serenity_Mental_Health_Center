package lk.ijse.mental_health_therapy_center.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "therapy_sessions")
public class TherapySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private int sessionId;

    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.SCHEDULED;

    @Column(name = "notes", length = 500)
    private String notes;

    // Many Sessions → One Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Many Sessions → One Therapist
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;

    // Many Sessions → One TherapyProgram
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;

    public enum Status {
        SCHEDULED,
        COMPLETED,
        CANCELLED,
        RESCHEDULED
    }

    public TherapySession() {}
    public TherapySession(LocalDateTime sessionDate, Patient patient, Therapist therapist, TherapyProgram therapyProgram) {
        this.sessionDate = sessionDate;
        this.patient = patient;
        this.therapist = therapist;
        this.therapyProgram = therapyProgram;
    }

    public int getSessionId(){
        return sessionId;
    }
    public LocalDateTime getSessionDate(){
        return sessionDate;
    }
    public void setSessionDate(LocalDateTime d){
        this.sessionDate = d;
    }
    public Status getStatus(){
        return status;
    }
    public void setStatus(Status s){
        this.status = s;
    }
    public String getNotes(){
        return notes;
    }
    public void setNotes(String n){
        this.notes = n;
    }
    public Patient getPatient(){
        return patient;
    }
    public void setPatient(Patient p){
        this.patient = p;
    }
    public Therapist getTherapist(){
        return therapist;
    }
    public void setTherapist(Therapist t){
        this.therapist = t;
    }
    public TherapyProgram getTherapyProgram(){
        return therapyProgram;
    }
    public void setTherapyProgram(TherapyProgram p) {
        this.therapyProgram = p;
    }
}

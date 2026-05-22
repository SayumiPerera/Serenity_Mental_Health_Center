package lk.ijse.mental_health_therapy_center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")


    private String patientName;
    private LocalDate date;
    private double amount;

    @OneToOne
    @JoinColumn(name = "session_id")
    private TherapySession therapySession;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private TherapyProgram program;

    public int getId() {
        return 0;
    }

    public void setId(int id) {
    }
}

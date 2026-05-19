package lk.ijse.mental_health_therapy_center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (name = "therapy_program")
public class TherapyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private int id;

    @Column(name = "program_name")
    private String name;

    private String duration;
    private double fee;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Registration> registration;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Payment> payment;
}

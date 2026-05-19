package lk.ijse.mental_health_therapy_center.dto;

import jakarta.persistence.Entity;
import lk.ijse.gdse.entity.Registration;
import lk.ijse.gdse.entity.TherapySession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private int id;
    private String name;
    private String nic ;
    private int age;
    private String gender;
    private String phone;
    private String email;

}

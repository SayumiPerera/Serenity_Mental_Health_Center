package lk.ijse.mental_health_therapy_center.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

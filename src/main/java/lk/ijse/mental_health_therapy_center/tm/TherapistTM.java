package lk.ijse.mental_health_therapy_center.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapistTM {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String program;
}
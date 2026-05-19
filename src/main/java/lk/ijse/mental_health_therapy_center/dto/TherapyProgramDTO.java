package lk.ijse.mental_health_therapy_center.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapyProgramDTO {
    private int id;
    private String name;
    private String duration;
    private double fee;
}

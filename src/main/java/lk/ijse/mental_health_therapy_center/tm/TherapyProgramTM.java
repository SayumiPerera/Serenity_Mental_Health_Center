package lk.ijse.mental_health_therapy_center.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapyProgramTM {
    private int id;
    private String name;
    private String duration;
    private double fee;

    public String getProgramName() {
        return "";
    }

    public String getProgramId() {
        return "";
    }
}

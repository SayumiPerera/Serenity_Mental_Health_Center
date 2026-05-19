package lk.ijse.mental_health_therapy_center.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapySessionTM {
    private int sessionId;
    private LocalDate date;
    private int patientId;
    private int programId;
    private int therapistId;
    private String sessionName;
    private String patientName;
    private String programName;
    private String therapistName;
    private LocalDate sessionDate;
}

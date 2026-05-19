package lk.ijse.mental_health_therapy_center.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTM {
    private int id;
    private int sessionId;
    private String patientName;
    private int programId;
    private LocalDate date;
    private double amount;


}

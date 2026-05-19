package lk.ijse.mental_health_therapy_center.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTM {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String role;
}

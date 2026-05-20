package lk.ijse.mental_health_therapy_center.bo.custom;

import lk.ijse.mental_health_therapy_center.bo.SuperBO;
import lk.ijse.mental_health_therapy_center.dto.UserDTO;

public interface LoginBO extends SuperBO {
    boolean authenticate(String username, String password);
    String getRole();
    boolean isWrongPsw();

    UserDTO getUser();
}

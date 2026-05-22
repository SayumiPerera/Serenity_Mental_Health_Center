package lk.ijse.mental_health_therapy_center.bo.custom;

import lk.ijse.mental_health_therapy_center.bo.SuperBO;
import lk.ijse.mental_health_therapy_center.dto.TherapySessionDTO;

import java.util.ArrayList;

public interface TherapySessionBO extends SuperBO {

    boolean saveTherapySession(TherapySessionDTO dto) throws Exception;

    boolean updateTherapySession(TherapySessionDTO dto) throws Exception;

    boolean deleteTherapySession(int sessionId);

    TherapySessionDTO searchTherapySession(int sessionId);

    ArrayList<TherapySessionDTO> getAllTherapySessions() throws Exception;

    String getNextSessionId();
}
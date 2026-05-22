package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center.entity.TherapySession;

public interface TherapySessionDAO extends CrudDAO<TherapySession> {

    boolean delete(Integer sessionId);

    TherapySession search(int sessionId);

    String getNextId();

    boolean delete(int sessionId);
}
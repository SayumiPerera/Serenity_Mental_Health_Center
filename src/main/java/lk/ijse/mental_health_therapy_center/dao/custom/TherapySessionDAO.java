package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center.entity.TherapySession;

public interface TherapySessionDAO extends CrudDAO<TherapySession, Integer> {

    TherapySession search(int sessionId);

    String getNextId();
}
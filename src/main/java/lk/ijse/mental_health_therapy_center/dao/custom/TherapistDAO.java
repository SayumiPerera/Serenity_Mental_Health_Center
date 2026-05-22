package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center.entity.Therapist;

import java.util.ArrayList;
import java.util.Optional;

public interface TherapistDAO extends CrudDAO<Therapist> {

    String getNextId();

    ArrayList<String> getAllTherapistNames();

    String getTherapistNameById(int therapistId);

    String getTherapistIdByName(String therapistName);

    Optional<Therapist> findByPK(int therapistId);

    Therapist search(int therapistId);
}
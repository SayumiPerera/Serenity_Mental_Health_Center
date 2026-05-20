package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center.entity.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PatientDAO extends CrudDAO<Patient> {

    List<Patient> searchPatient(String searchText);
    String getNextId();
    List<String> getPatientIdAndNames();
    ArrayList<String> getAllPatientNames();
    String getPatientNameById(int patientId);
    int getPatientIdByName(String patientName);
    Patient getPatientById(int patientId);
    Optional<Patient> findByPK(int patientId);
    long getTotalPatientCount();
    Map<String, Long> getPatientCountByGender();
}
package lk.ijse.mental_health_therapy_center.bo.custom;

import lk.ijse.mental_health_therapy_center.bo.SuperBO;
import lk.ijse.mental_health_therapy_center.dto.PatientDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PatientBO extends SuperBO {

    boolean addPatient(PatientDTO patientDTO);
    boolean updatePatient(PatientDTO patientDTO);
    boolean deletePatient(int id);
    List<PatientDTO> getAllPatient();
    List<PatientDTO> searchPatient(String searchText) throws Exception;
    String generatePatientId();
    int getNextPatientId();
    List<String> getPatientIdAndNames();
    String getPatientNameById(int patientId);
    int getPatientIdByName(String patientName);
    ArrayList<String> getAllPatientNames();
    long getTotalPatientCount();
    Map<String, Long> getPatientCountByGender();
}
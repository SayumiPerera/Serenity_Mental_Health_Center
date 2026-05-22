package lk.ijse.mental_health_therapy_center.bo.custom.impl;

import lk.ijse.mental_health_therapy_center.bo.custom.PatientBO;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.dao.custom.PatientDAO;
import lk.ijse.mental_health_therapy_center.dto.PatientDTO;
import lk.ijse.mental_health_therapy_center.entity.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientBOImpl implements PatientBO {

    private final PatientDAO patientDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT);

    @Override
    public boolean addPatient(PatientDTO patientDTO) throws Exception {

        return patientDAO.save(
                new Patient(
                        patientDTO.getId(),
                        patientDTO.getName(),
                        patientDTO.getNic(),
                        patientDTO.getAge(),
                        patientDTO.getGender(),
                        patientDTO.getPhone(),
                        patientDTO.getEmail()
                )
        );
    }

    @Override
    public boolean updatePatient(PatientDTO patientDTO) throws Exception {

        return patientDAO.update(
                new Patient(
                        patientDTO.getId(),
                        patientDTO.getName(),
                        patientDTO.getNic(),
                        patientDTO.getAge(),
                        patientDTO.getGender(),
                        patientDTO.getPhone(),
                        patientDTO.getEmail()
                )
        );
    }

    @Override
    public boolean deletePatient(int id) {
        return patientDAO.delete(id);
    }

    @Override
    public List<PatientDTO> getAllPatient() throws Exception {

        List<Patient> patients = patientDAO.getAll();
        List<PatientDTO> patientDTOList = new ArrayList<>();

        for (Patient patient : patients) {

            patientDTOList.add(
                    new PatientDTO(
                            patient.getId(),
                            patient.getName(),
                            patient.getNic(),
                            patient.getAge(),
                            patient.getGender(),
                            patient.getPhone(),
                            patient.getEmail()
                    )
            );
        }

        return patientDTOList;
    }

    @Override
    public List<PatientDTO> searchPatient(String searchText) throws Exception {

        try {
            if (searchText == null || searchText.trim().isEmpty()) {
                throw new IllegalArgumentException("Search text cannot be empty");
            }

            List<Patient> patients = patientDAO.searchPatient(searchText);

            List<PatientDTO> patientDTOList = new ArrayList<>();

            for (Patient patient : patients) {

                patientDTOList.add(
                        new PatientDTO(
                                patient.getId(),
                                patient.getName(),
                                patient.getNic(),
                                patient.getAge(),
                                patient.getGender(),
                                patient.getPhone(),
                                patient.getEmail()
                        )
                );
            }

            return patientDTOList;

        } catch (Exception e) {

            Logger.getLogger(PatientBOImpl.class.getName())
                    .log(Level.SEVERE, "Error searching patients", e);

            throw new Exception(
                    "Failed to search patients : " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public String generatePatientId() {
        return patientDAO.getNextId();
    }

    @Override
    public int getNextPatientId() {

        String nextId = patientDAO.getNextId();

        if (nextId == null) {
            return 1;
        }

        return Integer.parseInt(nextId);
    }

    @Override
    public List<String> getPatientIdAndNames() {
        return patientDAO.getPatientIdAndNames();
    }

    @Override
    public String getPatientNameById(int patientId) {
        return patientDAO.getPatientNameById(patientId);
    }

    @Override
    public int getPatientIdByName(String patientName) {
        return patientDAO.getPatientIdByName(patientName);
    }

    @Override
    public ArrayList<String> getAllPatientNames() {
        return patientDAO.getAllPatientNames();
    }

    @Override
    public long getTotalPatientCount() {
        return patientDAO.getTotalPatientCount();
    }

    @Override
    public Map<String, Long> getPatientCountByGender() {
        return patientDAO.getPatientCountByGender();
    }

    @Override
    public boolean save(PatientDTO patientDTO) {
        return false;
    }

    @Override
    public boolean update(PatientDTO patientDTO) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<PatientDTO> getAll() {
        return List.of();
    }

    @Override
    public int generateNextId() {
        return 0;
    }
}
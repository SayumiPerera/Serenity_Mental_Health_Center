package lk.ijse.mental_health_therapy_center.controller;

import lk.ijse.mental_health_therapy_center.bo.custom.PatientBO;
import lk.ijse.mental_health_therapy_center.entity.Patient;
import lk.ijse.mental_health_therapy_center.exception.RegistrationException;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * PatientController — Façade between Patient UI panels and PatientService.
 */
public class PatientController {

    private final PatientBO patientBO = new PatientBO();

    /**
     * Register a new patient.
     * Called from PatientFormDialog (Add mode).
     */
    public Patient registerPatient(String firstName, String lastName, String email, String phone, LocalDate dob, String address, String medicalHistory) throws RegistrationException {
        return patientBO.registerPatient(firstName, lastName, email, phone, dob, address, medicalHistory);
    }

    /**
     * Update an existing patient.
     * Called from PatientFormDialog (Edit mode).
     */
    public boolean updatePatient(Patient patient) throws RegistrationException {
        return patientBO.updatePatient(patient);
    }

    /**
     * Delete a patient by ID.
     * Called when Delete button is confirmed.
     */
    public boolean deletePatient(int patientId) {
        return patientBO.deletePatient(patientId);
    }

    /**
     * Enroll a patient in a therapy program.
     * Called from the "Enroll in Program" button.
     */
    public boolean enrollInProgram(int patientId, int programId) throws RegistrationException {
        return patientBO.enrollInProgram(patientId, programId);
    }

    /** Load all patients for the table. */
    public List<Patient> getAllPatients() {
        return patientBO.getAllPatients();
    }

    /** Search patients by name keyword. */
    public List<Patient> searchByName(String keyword) {
        return patientBO.searchByName(keyword);
    }

    /** Load all patients with their enrolled programs (uses JOIN FETCH). */
    public List<Patient> getPatientsWithPrograms() {
        return patientBO.getPatientsWithPrograms();
    }

    /**
     * HQL query — patients enrolled in ALL therapy programs.
     * Used in the Admin Reports tab.
     */
    public List<Patient> getPatientsEnrolledInAllPrograms() {
        return patientBO.getPatientsInAllPrograms();
    }

    /** Find a single patient by ID. */
    public Optional<Patient> findById(int patientId) {
        return patientBO.findById(patientId);
    }
}

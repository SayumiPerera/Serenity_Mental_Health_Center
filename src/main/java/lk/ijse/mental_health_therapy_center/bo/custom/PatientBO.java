package lk.ijse.mental_health_therapy_center.bo.custom;


import lk.ijse.mental_health_therapy_center.dao.custom.PatientDAO;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center.entity.Patient;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;
import lk.ijse.mental_health_therapy_center.exception.RegistrationException;
import lk.ijse.mental_health_therapy_center.util.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * PatientService — business logic for patient registration and management.
 * Acts as a Façade over PatientDAO and TherapyProgramDAO.
 */
public interface PatientBO {

    private final PatientDAO patientDAO  = new PatientDAO();
    private final TherapyProgramDAO programDAO  = new TherapyProgramDAO();

    // ── REGISTER PATIENT
    public Patient registerPatient(String firstName, String lastName, String email, String phone, LocalDate dob, String address, String medicalHistory) throws RegistrationException {

        // Validate inputs
        if (!Validator.isValidName(firstName))
            throw new RegistrationException("First name: " + Validator.getNameError());
        if (!Validator.isValidName(lastName))
            throw new RegistrationException("Last name: " + Validator.getNameError());
        if (!Validator.isValidEmail(email))
            throw new RegistrationException(Validator.getEmailError());
        if (!Validator.isValidPhone(phone))
            throw new RegistrationException(Validator.getPhoneError());
        if (dob == null || dob.isAfter(LocalDate.now()))
            throw new RegistrationException("Date of birth is invalid.");
        if (!Validator.isNotEmpty(address))
            throw new RegistrationException("Address is required.");

        // Check duplicate email
        if (patientDAO.findByEmail(email).isPresent())
            throw new RegistrationException("A patient with email '" + email + "' already exists.");

        Patient patient = new Patient(firstName, lastName, email, phone, dob, address, medicalHistory);
        boolean saved = patientDAO.save(patient);

        if (!saved)
            throw new RegistrationException("Failed to save patient — database error.");

        return patient;
    }

    // ── ENROLL IN PROGRAM
    public boolean enrollInProgram(int patientId, int programId) throws RegistrationException {
        Optional<TherapyProgram> program = programDAO.findById(programId);
        if (program.isEmpty())
            throw new RegistrationException("Therapy program not found.");

        boolean enrolled = patientDAO.enrollInProgram(patientId, program.get());
        if (!enrolled)
            throw new RegistrationException("Patient is already enrolled in this program.");

        return true;
    }

    // ── UPDATE PATIENT
    public boolean updatePatient(Patient patient) throws RegistrationException {
        if (!Validator.isValidEmail(patient.getEmail()))
            throw new RegistrationException(Validator.getEmailError());
        if (!Validator.isValidPhone(patient.getPhone()))
            throw new RegistrationException(Validator.getPhoneError());
        return patientDAO.update(patient);
    }

    // ── DELETE PATIENT
    public boolean deletePatient(int patientId) {
        return patientDAO.delete(patientId);
    }

    // ── QUERIES
    public Optional<Patient> findById(int id) {
        return patientDAO.findById(id);
    }
    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }
    public List<Patient> searchByName(String keyword) {
        return patientDAO.searchByName(keyword);
    }
    public List<Patient> getPatientsWithPrograms(){
        return patientDAO.findAllWithPrograms();
    }
    public List<Patient> getPatientsInAllPrograms(){
        return patientDAO.findPatientsEnrolledInAllPrograms();
    }
}

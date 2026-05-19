package lk.ijse.mental_health_therapy_center.bo.custom;


import lk.ijse.mental_health_therapy_center.dao.custom.TherapistDAO;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center.entity.Therapist;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;
import lk.ijse.mental_health_therapy_center.exception.RegistrationException;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.util.Validator;

import java.util.List;
import java.util.Optional;

/** TherapistService — business logic for therapist management (Admin only). */
public interface TherapistBO {

    // Uses DAOFactory (Factory pattern) instead of new TherapistDAO() directly
    private final TherapistDAO    therapistDAO = DAOFactory.getInstance().getTherapistDAO();
    private final TherapyProgramDAO programDAO = DAOFactory.getInstance().getTherapyProgramDAO();

    public Therapist addTherapist(String firstName, String lastName, String email, String phone, String specialization) throws RegistrationException {
        if (!Validator.isValidName(firstName))throw new RegistrationException("First name: " + Validator.getNameError());
        if (!Validator.isValidName(lastName))throw new RegistrationException("Last name: "  + Validator.getNameError());
        if (!Validator.isValidEmail(email))throw new RegistrationException(Validator.getEmailError());
        if (!Validator.isValidPhone(phone))throw new RegistrationException(Validator.getPhoneError());
        if (!Validator.isNotEmpty(specialization)) throw new RegistrationException("Specialization is required.");

        Therapist t = new Therapist(firstName, lastName, email, phone, specialization);
        if (!therapistDAO.save(t)) throw new RegistrationException("Failed to add therapist.");
        return t;
    }

    public boolean updateTherapist(Therapist therapist) throws RegistrationException {
        if (!Validator.isValidEmail(therapist.getEmail()))
            throw new RegistrationException(Validator.getEmailError());
        if (!Validator.isValidPhone(therapist.getPhone()))
            throw new RegistrationException(Validator.getPhoneError());
        return therapistDAO.update(therapist);
    }

    public boolean deleteTherapist(int id){
        return therapistDAO.delete(id);
    }
    public Optional<Therapist> findById(int id){
        return therapistDAO.findById(id);
    }
    public List<Therapist> getAllTherapists(){
        return therapistDAO.findAll();
    }
    public List<Therapist> getAvailableTherapists(){
        return therapistDAO.findAvailable();
    }
    public List<Therapist> findBySpecialization(String s){
        return therapistDAO.findBySpecialization(s);
    }

    public boolean setAvailability(int id, boolean available) {
        Optional<Therapist> t = therapistDAO.findById(id);
        if (t.isEmpty()) return false;
        t.get().setAvailable(available);
        return therapistDAO.update(t.get());
    }

    /** Assign a therapist to a therapy program (Many-to-Many) */
    public boolean assignToProgram(int therapistId, int programId) throws RegistrationException {
        Therapist t = therapistDAO.findById(therapistId).orElseThrow(() ->
                new RegistrationException("Therapist not found."));
        TherapyProgram p = programDAO.findById(programId).orElseThrow(() ->
                new RegistrationException("Program not found."));
        if (t.getAssignedPrograms().stream().anyMatch(ap -> ap.getProgramId() == programId)) throw new RegistrationException("Therapist is already assigned to this program.");
        t.getAssignedPrograms().add(p);
        return therapistDAO.update(t);
    }

    /** Remove a therapist from a therapy program */
    public boolean removeFromProgram(int therapistId, int programId) throws RegistrationException {
        Therapist t = therapistDAO.findById(therapistId).orElseThrow(() ->
                new RegistrationException("Therapist not found."));
        t.getAssignedPrograms().removeIf(p -> p.getProgramId() == programId);
        return therapistDAO.update(t);
    }
}

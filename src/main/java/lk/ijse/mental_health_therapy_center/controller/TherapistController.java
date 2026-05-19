package lk.ijse.mental_health_therapy_center.controller;

import lk.ijse.mental_health_therapy_center.bo.custom.TherapistBO;
import lk.ijse.mental_health_therapy_center.entity.Therapist;
import lk.ijse.mental_health_therapy_center.exception.RegistrationException;

import java.util.List;
import java.util.Optional;

/**
 * TherapistController — Façade between Therapist UI and TherapistService.
 * Only Admin-facing operations live here.
 */
public class TherapistController {

    private final TherapistBO therapistBO = new TherapistBO();

    /** Add a new therapist. Called from TherapistFormDialog (Add mode). */
    public Therapist addTherapist(String firstName, String lastName, String email, String phone, String specialization) throws RegistrationException {
        return therapistBO.addTherapist(firstName, lastName, email, phone, specialization);
    }

    /** Update an existing therapist. Called from TherapistFormDialog (Edit mode). */
    public boolean updateTherapist(Therapist therapist) throws RegistrationException {
        return therapistBO.updateTherapist(therapist);
    }

    /** Delete a therapist. Called when Delete is confirmed in the table. */
    public boolean deleteTherapist(int therapistId) {
        return therapistBO.deleteTherapist(therapistId);
    }

    /** Toggle a therapist's availability (available / on leave). */
    public boolean setAvailability(int therapistId, boolean available) {
        return therapistBO.setAvailability(therapistId, available);
    }

    /** Load all therapists for the table. */
    public List<Therapist> getAllTherapists() {
        return therapistBO.getAllTherapists();
    }

    /** Load only therapists who are currently available. Used in session booking. */
    public List<Therapist> getAvailableTherapists() {
        return therapistBO.getAvailableTherapists();
    }

    /** Find therapists by specialization keyword. */
    public List<Therapist> findBySpecialization(String specialization) {
        return therapistBO.findBySpecialization(specialization);
    }

    /** Find a single therapist by ID. */
    public Optional<Therapist> findById(int therapistId) {
        return therapistBO.findById(therapistId);
    }
}

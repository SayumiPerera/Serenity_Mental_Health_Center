package lk.ijse.mental_health_therapy_center.controller;

import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;
import lk.ijse.mental_health_therapy_center.exception.RegistrationException;

import java.util.List;
import java.util.Optional;

/**
 * TherapyProgramController — Façade between Program UI and TherapyProgramService.
 * Admin-only CRUD operations.
 */
public class TherapyProgramController {

    private final TherapyProgramBO programBO = new TherapyProgramBO();

    /** Add a new therapy program. */
    public TherapyProgram addProgram(String code, String name, String duration, double fee, String description) throws RegistrationException {
        return programBO.addProgram(code, name, duration, fee, description);
    }

    /** Update an existing therapy program. */
    public boolean updateProgram(TherapyProgram program) throws RegistrationException {
        return programBO.updateProgram(program);
    }

    /** Delete a therapy program. */
    public boolean deleteProgram(int programId) {
        return programBO.deleteProgram(programId);
    }

    /** Load all programs for tables and dropdowns. */
    public List<TherapyProgram> getAllPrograms() {
        return programBO.getAllPrograms();
    }

    /** Find a single program by ID. */
    public Optional<TherapyProgram> findById(int programId) {
        return programBO.findById(programId);
    }
}

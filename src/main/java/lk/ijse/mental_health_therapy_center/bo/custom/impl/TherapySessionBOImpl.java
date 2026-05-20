package lk.ijse.mental_health_therapy_center.bo.custom.impl;

import lk.ijse.mental_health_therapy_center.bo.custom.TherapySessionBO;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.dao.custom.PatientDAO;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapistDAO;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapySessionDAO;
import lk.ijse.mental_health_therapy_center.dto.TherapySessionDTO;
import lk.ijse.mental_health_therapy_center.entity.Patient;
import lk.ijse.mental_health_therapy_center.entity.Therapist;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;
import lk.ijse.mental_health_therapy_center.entity.TherapySession;

import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {

    TherapySessionDAO therapySessionDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPY_SESSION);
    PatientDAO patientDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);
    TherapistDAO therapistDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPIST);
    TherapyProgramDAO therapyProgramDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPY_PROGRAM);

    @Override
    public boolean saveTherapySession(TherapySessionDTO dto) {

        Patient patient = patientDAO.search(dto.getPatientId());
        Therapist therapist = therapistDAO.search(dto.getTherapistId());
        TherapyProgram program = therapyProgramDAO.search(dto.getProgramId());

        TherapySession therapySession = new TherapySession();

        therapySession.setDate(dto.getDate());
        therapySession.setSessionDate(dto.getSessionDate());
        therapySession.setSessionName(dto.getSessionName());

        therapySession.setPatientName(dto.getPatientName());
        therapySession.setProgramName(dto.getProgramName());
        therapySession.setTherapistName(dto.getTherapistName());

        therapySession.setPatient(patient);
        therapySession.setTherapist(therapist);
        therapySession.setTherapyProgram(program);

        return therapySessionDAO.save(therapySession);
    }

    @Override
    public boolean updateTherapySession(TherapySessionDTO dto) {

        Patient patient = patientDAO.search(dto.getPatientId());
        Therapist therapist = therapistDAO.search(dto.getTherapistId());
        TherapyProgram program = therapyProgramDAO.search(dto.getProgramId());

        TherapySession therapySession = new TherapySession();

        therapySession.setSessionId(dto.getSessionId());
        therapySession.setDate(dto.getDate());
        therapySession.setSessionDate(dto.getSessionDate());
        therapySession.setSessionName(dto.getSessionName());

        therapySession.setPatientName(dto.getPatientName());
        therapySession.setProgramName(dto.getProgramName());
        therapySession.setTherapistName(dto.getTherapistName());

        therapySession.setPatient(patient);
        therapySession.setTherapist(therapist);
        therapySession.setTherapyProgram(program);

        return therapySessionDAO.update(therapySession);
    }

    @Override
    public boolean deleteTherapySession(int sessionId) {
        return therapySessionDAO.delete(sessionId);
    }

    @Override
    public TherapySessionDTO searchTherapySession(int sessionId) {

        TherapySession therapySession = therapySessionDAO.search(sessionId);

        if (therapySession == null) {
            return null;
        }

        return new TherapySessionDTO(
                therapySession.getSessionId(),
                therapySession.getDate(),
                therapySession.getPatient().getId(),
                therapySession.getTherapyProgram().getId(),
                therapySession.getTherapist().getId(),
                therapySession.getSessionName(),
                therapySession.getPatientName(),
                therapySession.getProgramName(),
                therapySession.getTherapistName(),
                therapySession.getSessionDate()
        );
    }

    @Override
    public ArrayList<TherapySessionDTO> getAllTherapySessions() {

        List<TherapySession> sessions = therapySessionDAO.getAll();
        ArrayList<TherapySessionDTO> dtoList = new ArrayList<>();

        for (TherapySession therapySession : sessions) {

            dtoList.add(new TherapySessionDTO(
                    therapySession.getSessionId(),
                    therapySession.getDate(),
                    therapySession.getPatient().getId(),
                    therapySession.getTherapyProgram().getId(),
                    therapySession.getTherapist().getId(),
                    therapySession.getSessionName(),
                    therapySession.getPatientName(),
                    therapySession.getProgramName(),
                    therapySession.getTherapistName(),
                    therapySession.getSessionDate()
            ));
        }

        return dtoList;
    }

    @Override
    public String getNextSessionId() {
        return therapySessionDAO.getNextId();
    }
}
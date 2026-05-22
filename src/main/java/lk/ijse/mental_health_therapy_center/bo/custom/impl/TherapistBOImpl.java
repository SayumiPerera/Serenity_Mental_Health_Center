package lk.ijse.mental_health_therapy_center.bo.custom.impl;

import lk.ijse.mental_health_therapy_center.bo.custom.TherapistBO;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapistDAO;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center.dto.TherapistDTO;
import lk.ijse.mental_health_therapy_center.entity.Therapist;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {

    TherapistDAO therapistDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPIST);
    TherapyProgramDAO therapyProgramDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPY_PROGRAM);

    @Override
    public boolean save(TherapistDTO therapistDTO) throws Exception {

        TherapyProgram program = therapyProgramDAO.findById(Integer.parseInt(therapistDTO.getProgram()));
        Therapist therapist = new Therapist();
        therapist.setName(therapistDTO.getName());
        therapist.setEmail(therapistDTO.getEmail());
        therapist.setPhone(therapistDTO.getPhone());
        therapist.setTherapyProgram(program);

        return therapistDAO.save(therapist);
    }

    @Override
    public boolean update(TherapistDTO therapistDTO) throws Exception {

        TherapyProgram program = therapyProgramDAO.findById(Integer.parseInt(therapistDTO.getProgram()));

        Therapist therapist = new Therapist();

        therapist.setId(therapistDTO.getId());
        therapist.setName(therapistDTO.getName());
        therapist.setEmail(therapistDTO.getEmail());
        therapist.setPhone(therapistDTO.getPhone());
        therapist.setTherapyProgram(program);

        return therapistDAO.update(therapist);
    }

    @Override
    public boolean delete(int id) throws Exception {
        return therapistDAO.delete(String.valueOf(id));
    }

    @Override
    public List<TherapistDTO> getAll() throws Exception {

        List<Therapist> therapistList = therapistDAO.getAll();
        List<TherapistDTO> dtoList = new ArrayList<>();

        for (Therapist therapist : therapistList) {
            dtoList.add(
                    new TherapistDTO(
                            therapist.getId(),
                            therapist.getName(),
                            therapist.getEmail(),
                            therapist.getPhone(),
                            therapist.getTherapyProgram() != null
                                    ? String.valueOf(therapist.getTherapyProgram().getId())
                                    : null
                    )
            );
        }

        return dtoList;
    }

    @Override
    public TherapistDTO searchById(int id) throws Exception {

        Therapist therapist = therapistDAO.search(String.valueOf(id));

        if (therapist == null) {
            return null;
        }

        return new TherapistDTO(
                therapist.getId(),
                therapist.getName(),
                therapist.getEmail(),
                therapist.getPhone(),
                therapist.getTherapyProgram() != null
                        ? String.valueOf(therapist.getTherapyProgram().getId())
                        : null
        );
    }

    @Override
    public boolean saveTherapist(TherapistDTO dto) {
        return false;
    }

    @Override
    public boolean updateTherapist(TherapistDTO dto) {
        return false;
    }

    @Override
    public boolean deleteTherapist(String id) {
        return false;
    }

    @Override
    public Object getAllTherapists() {
        return null;
    }

    @Override
    public String getNextTherapistId() {
        return "";
    }
}
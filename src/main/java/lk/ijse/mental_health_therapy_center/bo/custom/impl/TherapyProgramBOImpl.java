package lk.ijse.mental_health_therapy_center.bo.custom.impl;

import lk.ijse.mental_health_therapy_center.bo.custom.TherapyProgramBO;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center.dto.TherapyProgramDTO;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBOImpl implements TherapyProgramBO {

    TherapyProgramDAO therapyProgramDAO =
            DAOFactory.getDaoFactory()
                    .getDAO(DAOFactory.DAOTypes.THERAPY_PROGRAM);

    @Override
    public List<TherapyProgramDTO> getAllPrograms() throws Exception {

        List<TherapyProgram> programs = therapyProgramDAO.getAll();

        ArrayList<TherapyProgramDTO> dtoList = new ArrayList<>();

        for (TherapyProgram program : programs) {

            dtoList.add(new TherapyProgramDTO(
                    program.getId(),
                    program.getName(),
                    program.getDuration(),
                    program.getFee()
            ));
        }

        return dtoList;
    }

    @Override
    public boolean addTherapyProgram(TherapyProgramDTO therapyProgramDTO) throws Exception {

        TherapyProgram therapyProgram = new TherapyProgram();

        therapyProgram.setId(therapyProgramDTO.getId());
        therapyProgram.setName(therapyProgramDTO.getName());
        therapyProgram.setDuration(therapyProgramDTO.getDuration());
        therapyProgram.setFee(therapyProgramDTO.getFee());

        return therapyProgramDAO.save(therapyProgram);
    }

    @Override
    public boolean updateTherapyProgram(TherapyProgramDTO therapyProgramDTO) throws Exception {

        TherapyProgram therapyProgram = new TherapyProgram();

        therapyProgram.setId(therapyProgramDTO.getId());
        therapyProgram.setName(therapyProgramDTO.getName());
        therapyProgram.setDuration(therapyProgramDTO.getDuration());
        therapyProgram.setFee(therapyProgramDTO.getFee());

        return therapyProgramDAO.update(therapyProgram);
    }

    @Override
    public boolean deleteTherapyProgram(String id) throws Exception {
        return therapyProgramDAO.delete(id);
    }

    @Override
    public String getNextProgramId() {
        return therapyProgramDAO.getNextId();
    }

    @Override
    public ArrayList<String> getAllProgramNames() {
        return therapyProgramDAO.getAllProgramNames();
    }

    @Override
    public String getProgramNameById(int programId) {
        return therapyProgramDAO.getProgramNameById(programId);
    }

    @Override
    public String getProgramIdByName(String programName) {
        return therapyProgramDAO.getProgramIdByName(programName);
    }

    @Override
    public double getProgramFeeById(int programId) {
        return therapyProgramDAO.getProgramFeeById(programId);
    }
}
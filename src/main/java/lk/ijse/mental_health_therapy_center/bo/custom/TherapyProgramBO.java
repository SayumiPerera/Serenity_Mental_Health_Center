package lk.ijse.mental_health_therapy_center.bo.custom;

import lk.ijse.mental_health_therapy_center.bo.SuperBO;
import lk.ijse.mental_health_therapy_center.dto.TherapyProgramDTO;

import java.util.ArrayList;
import java.util.List;

public interface TherapyProgramBO extends SuperBO {

    List<TherapyProgramDTO> getAllPrograms();

    boolean addTherapyProgram(TherapyProgramDTO therapyProgramDTO);

    boolean updateTherapyProgram(TherapyProgramDTO therapyProgramDTO);

    boolean deleteTherapyProgram(String id);

    String getNextProgramId();

    ArrayList<String> getAllProgramNames();

    String getProgramNameById(int programId);

    String getProgramIdByName(String programName);

    double getProgramFeeById(int programId);
}
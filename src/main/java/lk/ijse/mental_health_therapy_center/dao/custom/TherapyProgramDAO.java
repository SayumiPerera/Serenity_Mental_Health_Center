package lk.ijse.mental_health_therapy_center.dao.custom;

import lk.ijse.mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.Optional;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram> {

    String getNextId();

    ArrayList<String> getAllProgramNames();

    String getProgramNameById(int programId);

    String getProgramIdByName(String programName);

    double getProgramFeeById(int programId);

    Optional<TherapyProgram> findByPK(int programId);

    TherapyProgram search(int programId);

    TherapyProgram findById(int i);
}
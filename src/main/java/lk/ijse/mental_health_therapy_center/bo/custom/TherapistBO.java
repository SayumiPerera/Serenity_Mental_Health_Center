package lk.ijse.mental_health_therapy_center.bo.custom;

import lk.ijse.mental_health_therapy_center.bo.SuperBO;
import lk.ijse.mental_health_therapy_center.dto.TherapistDTO;

import java.util.List;

public interface TherapistBO extends SuperBO {

    boolean save(TherapistDTO therapistDTO) throws Exception;

    boolean update(TherapistDTO therapistDTO) throws Exception;

    boolean delete(int id) throws Exception;

    List<TherapistDTO> getAll() throws Exception;

    TherapistDTO searchById(int id) throws Exception;
}
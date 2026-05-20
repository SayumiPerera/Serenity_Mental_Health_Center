package lk.ijse.mental_health_therapy_center.bo.custom.impl;

import lk.ijse.mental_health_therapy_center.bo.custom.LoginBO;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.dao.custom.LoginDAO;
import lk.ijse.mental_health_therapy_center.dto.UserDTO;
import lk.ijse.mental_health_therapy_center.entity.User;

public class LoginBOImpl implements LoginBO {
    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public String getRole() {
        return "";
    }

    @Override
    public boolean isWrongPsw() {
        return false;
    }

    @Override
    public UserDTO getUser() {
        return null;
    }
//    LoginDAO loginDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.LOGIN);
//
//    @Override
//    public boolean authenticate(String username, String password) {
//        try {
//            return loginDAO.authenticate(username, password);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public String getRole() {
//        return loginDAO.getRole();
//    }
//
//    @Override
//    public boolean isWrongPsw() {
//        return loginDAO.isWrongPsw();
//    }
//
//    @Override
//    public UserDTO getUser() {
//        User user = loginDAO.getUser();
//        if (user != null) {
//            return new UserDTO(user.getId(), user.getUsername(), user.getFullname(), user.getEmail(), user.getPassword(), user.getRole());
//        }
//        return null;
//    }
}
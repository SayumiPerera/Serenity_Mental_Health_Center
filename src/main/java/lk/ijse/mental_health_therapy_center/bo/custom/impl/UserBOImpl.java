package lk.ijse.mental_health_therapy_center.bo.custom.impl;

import lk.ijse.mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.dao.custom.UserDAO;
import lk.ijse.mental_health_therapy_center.dto.UserDTO;
import lk.ijse.mental_health_therapy_center.entity.User;
import lk.ijse.mental_health_therapy_center.exception.LoginException;
import lk.ijse.mental_health_therapy_center.tm.UserTM;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Map;

public class UserBOImpl implements UserBO {

    // Get UserDAO via DAOFactory (no direct Hibernate here)
    private final UserDAO userDAO =
            (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean authenticate(String username, String rawPassword)
            throws LoginException, Exception {

        User user = userDAO.findByUsername(username);

        if (user == null) {
            // Throw custom exception — LoginException will be caught in controller
            throw new LoginException("No account found for username: " + username);
        }

        // BCrypt.checkpw() compares plain-text against the stored hash
        return BCrypt.checkpw(rawPassword, user.getPassword());
    }

    @Override
    public String getUserRole(String username) throws LoginException, Exception {

        User user = userDAO.findByUsername(username);

        if (user == null) {
            throw new LoginException("User not found: " + username);
        }

        return user.getRole();   // "ADMIN" or "RECEPTIONIST"
    }

    @Override
    public void initializeDefaultUsers() {

    }

    @Override
    public User searchUserByEmail(String email) {
        return null;
    }

    @Override
    public User searchUserByAdminEmail(String adMail) {
        return null;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        return false;
    }

    @Override
    public List<UserDTO> getAllUser() {
        return List.of();
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }

    @Override
    public boolean addUser(UserDTO userDTO) {
        return false;
    }

    @Override
    public String generateNextUserId() {
        return "";
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        return false;
    }

    @Override
    public UserTM getAllUsers() {
        return null;
    }

    @Override
    public Map<String, String> getRecentUserLogins() {
        return Map.of();
    }
}
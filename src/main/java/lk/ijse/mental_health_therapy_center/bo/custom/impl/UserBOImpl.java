package lk.ijse.mental_health_therapy_center.bo.custom.impl;

import lk.ijse.mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center.dao.custom.UserDAO;
import lk.ijse.mental_health_therapy_center.entity.User;
import lk.ijse.mental_health_therapy_center.exception.LoginException;
import org.mindrot.jbcrypt.BCrypt;

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
}
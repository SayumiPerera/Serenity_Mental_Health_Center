package lk.ijse.mental_health_therapy_center.bo.custom;

import lk.ijse.mental_health_therapy_center.bo.SuperBO;
import lk.ijse.mental_health_therapy_center.dto.UserDTO;
import lk.ijse.mental_health_therapy_center.entity.User;
import lk.ijse.mental_health_therapy_center.exception.LoginException;
import lk.ijse.mental_health_therapy_center.tm.UserTM;

import java.util.List;
import java.util.Map;

public interface UserBO extends SuperBO {

    /**
     * Verifies plain-text password against the BCrypt hash stored in the DB.
     *
     * @return true if credentials are valid
     * @throws LoginException if username not found or any DB error
     */
    boolean authenticate(String username, String rawPassword) throws LoginException, Exception;

    /**
     * Returns the role ("ADMIN" or "RECEPTIONIST") for the given username.
     *
     * @throws LoginException if user not found
     */
    String getUserRole(String username) throws LoginException, Exception;


    void initializeDefaultUsers();

    User searchUserByEmail(String email);

    User searchUserByAdminEmail(String adMail);

    boolean updateUser(UserDTO userDTO);

    List<UserDTO> getAllUser();

    boolean deleteUser(String email);

    boolean addUser(UserDTO userDTO);

    String generateNextUserId();

    void initialize();

    boolean saveUser(UserDTO userDTO);

    UserTM getAllUsers();

    Map<String, String> getRecentUserLogins();



}
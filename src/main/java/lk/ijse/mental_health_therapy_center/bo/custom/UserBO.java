package lk.ijse.mental_health_therapy_center.bo.custom;

import lk.ijse.mental_health_therapy_center.exception.LoginException;

public interface UserBO {

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
}
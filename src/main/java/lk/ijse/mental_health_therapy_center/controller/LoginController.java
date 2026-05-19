package lk.ijse.mental_health_therapy_center.controller;


import lk.ijse.mental_health_therapy_center.entity.User;
import lk.ijse.mental_health_therapy_center.exception.LoginException;
import lk.ijse.mental_health_therapy_center.exception.RegistrationException;

/**
 * AuthController — Façade between the Login UI and AuthService.
 *
 * The UI calls this controller. The controller calls the service.
 * This keeps UI code free of business logic.
 */
public class LoginController {

    private final LoginBO loginBO = new LoginBO();

    /**
     * Called when the Login button is clicked.
     * @return the logged-in User object
     * @throws LoginException if credentials are wrong
     */
    public User login(String username, String password) throws LoginException {
        return loginBO.login(username, password);
    }

    /** Called when the Logout button is clicked. */
    public void logout() {
        loginBO.logout();
    }

    /**
     * Called from Settings dialog to change password.
     * @throws LoginException       if current password is wrong
     * @throws RegistrationException if new password fails validation
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) throws LoginException, RegistrationException {
        return loginBO.changePassword(userId, oldPassword, newPassword);
    }

    /**
     * Called from Settings dialog to change username.
     * @throws RegistrationException if new username is taken or invalid
     */
    public boolean changeUsername(int userId, String newUsername) throws RegistrationException {
        return loginBO.changeUsername(userId, newUsername);
    }

    /** @return the currently logged-in user (null if not logged in) */
    public User getCurrentUser() {
        return LoginBO.getLoggedInUser();
    }

    public boolean isAdmin(){
        return LoginBO.isAdmin();
    }
    public boolean isReceptionist() {
        return LoginBO.isReceptionist();
    }
}
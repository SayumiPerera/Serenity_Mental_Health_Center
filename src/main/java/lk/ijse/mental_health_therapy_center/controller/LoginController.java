package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.mental_health_therapy_center.bo.BOFactory;
import lk.ijse.mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center.exception.LoginException;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginController {

    @FXML private TextField     txtUsername;
    @FXML private PasswordField txtPassword;

    // Visible plain-text field for "show password" toggle
    @FXML private TextField txtPasswordVisible;

    // Eye icon button
    @FXML private Button btnTogglePassword;

    private boolean passwordVisible = false;

    private final UserBO userBO =
            (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);

    // ─── Regex patterns ──────────────────────────────────────────────────────
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_.]{3,30}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^.{6,}$");   // at least 6 chars

    // ─── Show / Hide password toggle ─────────────────────────────────────────
    @FXML
    void btnTogglePasswordOnAction(ActionEvent event) {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            // Copy masked text → visible field, show visible field
            txtPasswordVisible.setText(txtPassword.getText());
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);
            btnTogglePassword.setText("🙈");          // hide-eye icon text
        } else {
            // Copy visible text → masked field, hide visible field
            txtPassword.setText(txtPasswordVisible.getText());
            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);
            btnTogglePassword.setText("👁");           // show-eye icon text
        }
    }

    // ─── Login button ─────────────────────────────────────────────────────────
    @FXML
    void btnLoginOnAction(ActionEvent event) {

        // Always read from whichever field is currently active
        String username = txtUsername.getText().trim();
        String password = passwordVisible
                ? txtPasswordVisible.getText()
                : txtPassword.getText();

        // ── Validate inputs ──────────────────────────────────────────────────
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty.");
            return;
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            showError("Invalid username format.\n" +
                    "Only letters, digits, _ or . allowed (3–30 chars).");
            return;
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            showError("Password must be at least 6 characters.");
            return;
        }

        // ── Authenticate ─────────────────────────────────────────────────────
        try {
            boolean authenticated = userBO.authenticate(username, password);

            if (authenticated) {
                String role = userBO.getUserRole(username);
                navigateToDashboard(role);
            } else {
                showError("Invalid username or password.");
            }

        } catch (LoginException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("An unexpected error occurred. Please try again.");
        }
    }

    // ─── Role-based navigation ────────────────────────────────────────────────
    private void navigateToDashboard(String role) throws IOException {
        String fxmlPath;

        switch (role.toUpperCase()) {
            case "ADMIN":
                fxmlPath = "/view/AdminDashboardForm.fxml";
                break;
            case "RECEPTIONIST":
                fxmlPath = "/view/ReceptionistDashboardForm.fxml";
                break;
            default:
                showError("Unknown role: " + role);
                return;
        }

        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(
                getClass().getResource(fxmlPath))));
        stage.centerOnScreen();
    }

    // ─── Helper ───────────────────────────────────────────────────────────────
    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}
package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.mental_health_therapy_center.App;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void initialize() {
        System.out.println("LoginController initialized");
    }

    @FXML

    private void handleLogin(ActionEvent event) {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Basic validation
        if (username == null || username.isEmpty()) {
            showAlert("Username cannot be empty");
            return;
        }

        if (password == null || password.isEmpty()) {
            showAlert("Password cannot be empty");
            return;
        }

        // Dummy login check
        if (username.equals("admin") && password.equals("1234")) {

            showAlert("Login Successful!");

            System.out.println("User logged in successfully");

            try {

                App.setRoot("AdminLayout");

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Failed to load Admin Layout");
            }

        } else {
            showAlert("Invalid username or password");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
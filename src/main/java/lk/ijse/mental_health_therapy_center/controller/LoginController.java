package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mental_health_therapy_center.bo.BOFactory;
import lk.ijse.mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center.entity.User;
import lk.ijse.mental_health_therapy_center.util.PasswordUtil;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button BtnCreateNewAccount;

    @FXML
    private Button BtnForgotPw;

    @FXML
    private Button BtnSignIn;

    @FXML
    private PasswordField TxtPassword;

    @FXML
    private TextField TxtUserName;

    @FXML
    private AnchorPane root;

    private final UserBO userBO =
            (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);




    // =========================================
    // LOGIN
    // =========================================

    @FXML
    void BtnSignInOnAction(ActionEvent event) {

        try {
            String username = TxtUserName.getText().trim();
            String password = TxtPassword.getText().trim();

            // Validation
            if (username.isEmpty() || password.isEmpty()) {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Validation Error",
                        "Please enter username and password."
                );
                return;
            }

            // Search User
            User user = userBO.searchUserByEmail(username);
            if (user == null) {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Login Failed",
                        "User not found."
                );
                return;
            }

            // Verify Password
            if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Login Failed",
                        "Incorrect password."
                );
                return;
            }

            // Success
            loadDashboard(user);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(
                    Alert.AlertType.ERROR,
                    "System Error",
                    "Something went wrong while logging in."
            );
        }
    }

    // =========================================
    // LOAD DASHBOARD
    // =========================================

    private void loadDashboard(User user) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainLayout.fxml"));

        Parent rootNode = loader.load();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(rootNode));
        stage.setTitle("Mental Health Therapy Center");
        stage.centerOnScreen();
        stage.show();
    }

    // =========================================
    // LOAD FORMS
    // =========================================

    private void loadForm(String fxmlPath, String title) throws IOException {

        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(rootNode));
        stage.setTitle(title);
        stage.centerOnScreen();
    }

    // =========================================
    // ALERTS
    // =========================================

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // =========================================
    // ENTER KEY EVENTS
    // =========================================

    @FXML
    void txtUsername(ActionEvent event) {
        TxtPassword.requestFocus();
    }

    @FXML
    void txtPassword(ActionEvent event) {
        BtnSignInOnAction(event);
    }
}
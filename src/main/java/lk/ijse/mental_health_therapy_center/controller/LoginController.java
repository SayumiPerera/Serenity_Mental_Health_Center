package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mental_health_therapy_center.App;
import lk.ijse.mental_health_therapy_center.entity.User;
import org.hibernate.annotations.Parent;

import java.io.IOException;
import java.util.Objects;

//import static antlr.build.ANTLR.root;

public class LoginController {


    @FXML
    private AnchorPane root;

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



    private void loadDashboard(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/lk/ijse/mental_health_therapy_center/mainLayout.fxml")
        );
        Parent rootNode = loader.load();

        MainLayoutController mainLayoutController = loader.getController();
        mainLayoutController.setLoggedInUser(user);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene((javafx.scene.Parent) rootNode));
        stage.setTitle("Mental Health Therapy Center - Main Layout");
        stage.centerOnScreen();
        stage.show();
    }

    private void loadForm(String fxmlPath, String title) throws IOException {
        AnchorPane pane = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("/lk/ijse/mental_health_therapy_center/" + fxmlPath)
                )
        );
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(pane));
        stage.setTitle(title);
        stage.centerOnScreen();
        stage.show();
    }



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
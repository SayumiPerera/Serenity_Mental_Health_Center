package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mental_health_therapy_center.entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private Button BtnHome;

    @FXML
    private Button BtnLogOut;

    @FXML
    private Button BtnPatient;

    @FXML
    private Button BtnPayment;

    @FXML
    private Button BtnReg;

    @FXML
    private Button BtnSessionAppoinment;

    @FXML
    private Button BtnTherapist;

    @FXML
    private Button BtnTherapyPrograms;

    @FXML
    private Button BtnUser;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane sideAncPane;

    private User loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Default Home Screen
        navigateTo("adminLayout.fxml");
    }

    public void setLoggedInUser(User user) {

        this.loggedInUser = user;
        if (loggedInUser != null) {
            if (loggedInUser.getRole().equalsIgnoreCase("admin")) {
                setupAdminUI();
                navigateTo("adminLayout.fxml");
            } else {
                setupReceptionistUI();
                navigateTo("receptionistLayout.fxml");
            }
        }
    }

    private void setupAdminUI() {

        BtnUser.setDisable(false);
        BtnTherapist.setDisable(false);
        BtnTherapyPrograms.setDisable(false);

        BtnPatient.setDisable(false);
        BtnReg.setDisable(false);
        BtnSessionAppoinment.setDisable(false);
        BtnPayment.setDisable(false);
    }

    private void setupReceptionistUI() {

        BtnUser.setDisable(true);
        BtnTherapist.setDisable(true);

        BtnPatient.setDisable(false);
        BtnReg.setDisable(false);
        BtnSessionAppoinment.setDisable(false);
        BtnPayment.setDisable(false);
    }

    @FXML
    void BtnHomeOnAction(ActionEvent event) {

        if (loggedInUser != null &&
                loggedInUser.getRole().equalsIgnoreCase("admin")) {
            navigateTo("adminLayout.fxml");
        } else {
            navigateTo("receptionistLayout.fxml");
        }
    }

    @FXML
    void BtnPatientOnAction(ActionEvent event) {
        navigateTo("patient.fxml");
    }

    @FXML
    void BtnPaymentOnAction(ActionEvent event) {
        navigateTo("payment.fxml");
    }

    @FXML
    void BtnRegOnAction(ActionEvent event) {
        navigateTo("registration.fxml");
    }

    @FXML
    void BtnSessionAppoinmentOnAction(ActionEvent event) {
        navigateTo("therapySession.fxml");
    }

    @FXML
    void BtnTherapistOnAction(ActionEvent event) {
        navigateTo("therapist.fxml");
    }

    @FXML
    void BtnTherapyProgramsOnAction(ActionEvent event) {
        navigateTo("therapyProgram.fxml");
    }

    @FXML
    void BtnUserOnAction(ActionEvent event) {
        navigateTo("user.fxml");
    }

    @FXML
    void BtnLogOutOnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Logout Confirmation");
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Parent rootNode = FXMLLoader.load(
                        Objects.requireNonNull(
                                getClass().getResource("login.fxml")
                        )
                );

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(rootNode));
                stage.setTitle("Login");
                stage.centerOnScreen();
            }

        } catch (IOException e) {
            new Alert(
                    Alert.AlertType.ERROR,
                    "Failed to load Login Page!"
            ).show();
        }
    }

    public void navigateTo(String fxmlPath) {
        try {
            sideAncPane.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource(fxmlPath)
                    )
            );

            pane.prefWidthProperty().bind(sideAncPane.widthProperty());
            pane.prefHeightProperty().bind(sideAncPane.heightProperty());

            sideAncPane.getChildren().add(pane);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(
                    Alert.AlertType.ERROR,
                    "Failed to load page!"
            ).show();
        }
    }
}
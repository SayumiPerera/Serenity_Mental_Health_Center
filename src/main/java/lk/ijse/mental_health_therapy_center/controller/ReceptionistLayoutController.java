package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistLayoutController implements Initializable {

    @FXML
    private Button BtnFinacialReports;

    @FXML
    private Button BtnPatientTherapyHistory;

    @FXML
    private Button BtnTrackPayments;

    @FXML
    private Button BtnAppointmentManagement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showWelcomeMessage();
    }

    // =========================
    // BUTTON ACTIONS
    // =========================

    @FXML
    void BtnFinacialReportsOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Patients");
        alert.setHeaderText("Patient Management");
        alert.setContentText("""
                Open Patient Management Section
                
                • View Patients
                • Register Patients
                • Update Patient Details
                """);

        alert.showAndWait();
    }

    @FXML
    void BtnTrackPaymentsOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Therapy Sessions");
        alert.setHeaderText("Schedule Therapy Sessions");
        alert.setContentText("""
                Therapy Session Scheduler
                
                • Schedule Appointments
                • Manage Session Dates
                • Assign Therapists
                """);

        alert.showAndWait();
    }

    @FXML
    void BtnPatientTherapyHistoryOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payments");
        alert.setHeaderText("Payment Management");
        alert.setContentText("""
                Payment Section
                
                • Record Payments
                • View Payment History
                • Manage Outstanding Balances
                """);

        alert.showAndWait();
    }

    @FXML
    void BtnAppointmentManagementOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Financial Reports");
        alert.setHeaderText("Generate Financial Reports");
        alert.setContentText("""
                Financial Report Generator
                
                • Monthly Reports
                • Revenue Reports
                • Patient Payment Reports
                """);

        alert.showAndWait();
    }

    // =========================
    // WELCOME ALERT
    // =========================

    private void showWelcomeMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome");
        alert.setHeaderText("Receptionist Dashboard");
        alert.setContentText("""
                Welcome to Serenity Mental Health Therapy Center
                
                Manage appointments, patients,
                payments, and therapy sessions easily.
                
                Have a great day!
                """);

        alert.showAndWait();
    }
}
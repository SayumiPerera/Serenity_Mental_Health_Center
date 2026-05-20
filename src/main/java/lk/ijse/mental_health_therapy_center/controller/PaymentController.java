package lk.ijse.mental_health_therapy_center.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center.bo.BOFactory;
import lk.ijse.mental_health_therapy_center.bo.custom.PaymentBO;
import lk.ijse.mental_health_therapy_center.dto.PaymentDTO;
import lk.ijse.mental_health_therapy_center.tm.PaymentTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtSessionId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtProgramId;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtAmount;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<PaymentTM> tblPayments;

    @FXML
    private TableColumn<PaymentTM, Integer> colId;

    @FXML
    private TableColumn<PaymentTM, Integer> colSessionId;

    @FXML
    private TableColumn<PaymentTM, String> colPatientName;

    @FXML
    private TableColumn<PaymentTM, Integer> colProgramId;

    @FXML
    private TableColumn<PaymentTM, LocalDate> colDate;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    private final PaymentBO paymentBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        loadNextPaymentId();
        loadTableData();

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        try {

            int id = Integer.parseInt(txtId.getText());
            int sessionId = Integer.parseInt(txtSessionId.getText());
            String patientName = txtPatientName.getText();
            int programId = Integer.parseInt(txtProgramId.getText());
            LocalDate date = datePicker.getValue();
            double amount = Double.parseDouble(txtAmount.getText());

            if (patientName.isEmpty() || date == null) {
                new Alert(Alert.AlertType.ERROR,
                        "Please fill all fields!").show();
                return;
            }

            PaymentDTO paymentDTO = new PaymentDTO(id, sessionId, patientName, programId, date, amount);

            boolean isSaved = paymentBO.save(paymentDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Payment saved successfully!").show();

                refreshPage();

            } else {
                new Alert(Alert.AlertType.ERROR,
                        "Failed to save payment!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Invalid number format!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Error saving payment!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        try {
            int id = Integer.parseInt(txtId.getText());
            int sessionId = Integer.parseInt(txtSessionId.getText());
            String patientName = txtPatientName.getText();
            int programId = Integer.parseInt(txtProgramId.getText());
            LocalDate date = datePicker.getValue();
            double amount = Double.parseDouble(txtAmount.getText());

            PaymentDTO paymentDTO = new PaymentDTO(id, sessionId, patientName, programId, date, amount);

            boolean isUpdated = paymentBO.update(paymentDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Payment updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR,
                        "Failed to update payment!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Error updating payment!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        int id = Integer.parseInt(txtId.getText());

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Do you want to delete this payment?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = paymentBO.delete(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION,
                            "Payment deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR,
                            "Failed to delete payment!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();

                new Alert(Alert.AlertType.ERROR,
                        "Error deleting payment!").show();
            }
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        loadNextPaymentId();
    }

    @FXML
    void tblPaymentsOnMouseClicked(MouseEvent event) {

        PaymentTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {

            txtId.setText(String.valueOf(selectedPayment.getId()));
            txtSessionId.setText(String.valueOf(selectedPayment.getSessionId()));
            txtPatientName.setText(selectedPayment.getPatientName());
            txtProgramId.setText(String.valueOf(selectedPayment.getProgramId()));
            datePicker.setValue(selectedPayment.getDate());
            txtAmount.setText(String.valueOf(selectedPayment.getAmount()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void loadTableData() {

        try {
            List<PaymentDTO> paymentList = paymentBO.getAll();
            ObservableList<PaymentTM> observableList = FXCollections.observableArrayList();

            for (PaymentDTO dto : paymentList) {
                observableList.add(new PaymentTM(dto.getId(), dto.getSessionId(), dto.getPatientName(), dto.getProgramId(), dto.getDate(), dto.getAmount()));
            }
            tblPayments.setItems(observableList);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Failed to load payment data!").show();
        }
    }

    private void loadNextPaymentId() {
        try {
            int nextId = paymentBO.getNextId();
            txtId.setText(String.valueOf(nextId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() {

        loadNextPaymentId();
        loadTableData();
        clearFields();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void clearFields() {

        txtSessionId.clear();
        txtPatientName.clear();
        txtProgramId.clear();
        txtAmount.clear();

        datePicker.setValue(null);
    }
}
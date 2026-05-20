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
import lk.ijse.mental_health_therapy_center.bo.custom.PatientBO;
import lk.ijse.mental_health_therapy_center.dto.PatientDTO;
import lk.ijse.mental_health_therapy_center.tm.PatientTM;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtAge;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<PatientTM> tblPatients;

    @FXML
    private TableColumn<PatientTM, Integer> colId;

    @FXML
    private TableColumn<PatientTM, String> colName;

    @FXML
    private TableColumn<PatientTM, String> colNic;

    @FXML
    private TableColumn<PatientTM, Integer> colAge;

    @FXML
    private TableColumn<PatientTM, String> colGender;

    @FXML
    private TableColumn<PatientTM, String> colPhone;

    @FXML
    private TableColumn<PatientTM, String> colEmail;

    PatientBO patientBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.PATIENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        cmbGender.setItems(FXCollections.observableArrayList(
                "Male",
                "Female"
        ));

        loadAllPatients();
        generateNextId();

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        tblPatients.setOnMouseClicked(this::tblPatientsOnMouseClicked);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        try {

            String name = txtName.getText();
            String nic = txtNic.getText();
            int age = Integer.parseInt(txtAge.getText());
            String gender = cmbGender.getValue();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();

            if (name.isEmpty() || nic.isEmpty() || gender == null ||
                    phone.isEmpty() || email.isEmpty()) {

                new Alert(Alert.AlertType.ERROR,
                        "Please fill all fields").show();
                return;
            }

            PatientDTO patientDTO = new PatientDTO();

            patientDTO.setName(name);
            patientDTO.setNic(nic);
            patientDTO.setAge(age);
            patientDTO.setGender(gender);
            patientDTO.setPhone(phone);
            patientDTO.setEmail(email);

            boolean isSaved = patientBO.save(patientDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Patient Saved Successfully").show();
                clearFields();
                loadAllPatients();
                generateNextId();

            } else {
                new Alert(Alert.AlertType.ERROR,
                        "Failed To Save Patient").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Age must be a number").show();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Error : " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        try {
            int id = Integer.parseInt(txtId.getText());
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(id);
            patientDTO.setName(txtName.getText());
            patientDTO.setNic(txtNic.getText());
            patientDTO.setAge(Integer.parseInt(txtAge.getText()));
            patientDTO.setGender(cmbGender.getValue());
            patientDTO.setPhone(txtPhone.getText());
            patientDTO.setEmail(txtEmail.getText());

            boolean isUpdated = patientBO.update(patientDTO);

            if (isUpdated) {

                new Alert(Alert.AlertType.INFORMATION,
                        "Patient Updated Successfully").show();

                clearFields();
                loadAllPatients();
                generateNextId();

            } else {

                new Alert(Alert.AlertType.ERROR,
                        "Failed To Update Patient").show();
            }

        } catch (Exception e) {

            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR,
                    "Error : " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        try {

            int id = Integer.parseInt(txtId.getText());

            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Do you want to delete this patient?",
                    ButtonType.YES,
                    ButtonType.NO
            );

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {

                boolean isDeleted = patientBO.delete(id);

                if (isDeleted) {

                    new Alert(Alert.AlertType.INFORMATION,
                            "Patient Deleted Successfully").show();

                    clearFields();
                    loadAllPatients();
                    generateNextId();

                } else {

                    new Alert(Alert.AlertType.ERROR,
                            "Failed To Delete Patient").show();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR,
                    "Error : " + e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();
        generateNextId();

        tblPatients.getSelectionModel().clearSelection();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void tblPatientsOnMouseClicked(MouseEvent event) {

        PatientTM patientTM =
                tblPatients.getSelectionModel().getSelectedItem();

        if (patientTM != null) {

            txtId.setText(String.valueOf(patientTM.getId()));
            txtName.setText(patientTM.getName());
            txtNic.setText(patientTM.getNic());
            txtAge.setText(String.valueOf(patientTM.getAge()));
            cmbGender.setValue(patientTM.getGender());
            txtPhone.setText(patientTM.getPhone());
            txtEmail.setText(patientTM.getEmail());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void loadAllPatients() {

        try {

            List<PatientDTO> patientList = patientBO.getAll();

            ObservableList<PatientTM> observableList =
                    FXCollections.observableArrayList();

            for (PatientDTO dto : patientList) {

                observableList.add(
                        new PatientTM(
                                dto.getId(),
                                dto.getName(),
                                dto.getNic(),
                                dto.getAge(),
                                dto.getGender(),
                                dto.getPhone(),
                                dto.getEmail()
                        )
                );
            }

            tblPatients.setItems(observableList);

        } catch (Exception e) {

            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR,
                    "Failed To Load Patients").show();
        }
    }

    private void generateNextId() {

        try {

            int nextId = patientBO.generateNextId();
            txtId.setText(String.valueOf(nextId));

        } catch (Exception e) {

            txtId.setText("Auto");
        }
    }

    private void clearFields() {

        txtName.clear();
        txtNic.clear();
        txtAge.clear();
        cmbGender.setValue(null);
        txtPhone.clear();
        txtEmail.clear();
    }
}
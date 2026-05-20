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
import lk.ijse.mental_health_therapy_center.bo.custom.TherapistBO;
import lk.ijse.mental_health_therapy_center.bo.custom.TherapyProgramBO;
import lk.ijse.mental_health_therapy_center.dto.TherapistDTO;
import lk.ijse.mental_health_therapy_center.tm.TherapistTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistController implements Initializable {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private ComboBox<String> cmbProgram;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<TherapistTM> tblTherapist;

    @FXML
    private TableColumn<TherapistTM, String> colId;

    @FXML
    private TableColumn<TherapistTM, String> colName;

    @FXML
    private TableColumn<TherapistTM, String> colEmail;

    @FXML
    private TableColumn<TherapistTM, Integer> colPhone;

    @FXML
    private TableColumn<TherapistTM, String> colProgram;

    TherapistBO therapistBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPIST);
    TherapyProgramBO therapyProgramBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_PROGRAM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));

        loadPrograms();
        refreshPage();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phoneText = txtPhone.getText();
        String program = cmbProgram.getValue();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern =
                "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";

        if (name.isEmpty() || !name.matches(namePattern)) {
            new Alert(Alert.AlertType.ERROR,
                    "Invalid therapist name").show();
            return;
        }

        if (email.isEmpty() || !email.matches(emailPattern)) {
            new Alert(Alert.AlertType.ERROR,
                    "Invalid email").show();
            return;
        }

        if (program == null) {
            new Alert(Alert.AlertType.ERROR,
                    "Please select a therapy program").show();
            return;
        }

        int phone;

        try {
            phone = Integer.parseInt(phoneText);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "Invalid phone number").show();
            return;
        }

        TherapistDTO dto = new TherapistDTO(id, name, email, phone, program);

        boolean isSaved = therapistBO.saveTherapist(dto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Therapist Saved Successfully").show();

            refreshPage();

        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Failed To Save Therapist").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phoneText = txtPhone.getText();
        String program = cmbProgram.getValue();

        int phone;

        try {
            phone = Integer.parseInt(phoneText);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "Invalid phone number").show();
            return;
        }

        TherapistDTO dto = new TherapistDTO(id, name, email, phone, program);

        boolean isUpdated = therapistBO.updateTherapist(dto);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Therapist Updated Successfully").show();

            refreshPage();

        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Failed To Update Therapist").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Do you want to delete this therapist?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = therapistBO.deleteTherapist(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Therapist Deleted Successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR,
                        "Failed To Delete Therapist").show();
            }
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void tblTherapistOnMouseClicked(MouseEvent event) {

        TherapistTM therapist = tblTherapist.getSelectionModel().getSelectedItem();

        if (therapist != null) {

            txtId.setText(therapist.getId());
            txtName.setText(therapist.getName());
            txtEmail.setText(therapist.getEmail());
            txtPhone.setText(String.valueOf(therapist.getPhone()));
            cmbProgram.setValue(therapist.getProgram());

            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void loadPrograms() {

        ArrayList<String> programs = therapyProgramBO.getAllProgramNames();
        cmbProgram.setItems(FXCollections.observableArrayList(programs)
        );
    }

    private void loadTableData() {

        ArrayList<TherapistDTO> therapistList = (ArrayList<TherapistDTO>) therapistBO.getAllTherapists();
        ObservableList<TherapistTM> tmList = FXCollections.observableArrayList();

        for (TherapistDTO dto : therapistList) {
            TherapistTM tm = new TherapistTM(dto.getId(), dto.getName(), dto.getEmail(), dto.getPhone(), dto.getProgram());
            tmList.add(tm);
        }
        tblTherapist.setItems(tmList);
    }

    private void loadNextId() {

        String nextId = therapistBO.getNextTherapistId();
        txtId.setText(nextId);
    }

    private void refreshPage() {

        loadNextId();
        loadTableData();

        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();

        cmbProgram.getSelectionModel().clearSelection();

        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }
}
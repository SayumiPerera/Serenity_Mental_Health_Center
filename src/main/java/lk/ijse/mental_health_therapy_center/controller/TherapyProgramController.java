package lk.ijse.mental_health_therapy_center.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mental_health_therapy_center.bo.BOFactory;
import lk.ijse.mental_health_therapy_center.bo.custom.TherapyProgramBO;
import lk.ijse.mental_health_therapy_center.dto.TherapyProgramDTO;
import lk.ijse.mental_health_therapy_center.tm.TherapyProgramTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnReset;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<TherapyProgramTM, String> ColDuration;

    @FXML
    private TableColumn<TherapyProgramTM, Double> ColFee;

    @FXML
    private TableColumn<TherapyProgramTM, String> ColId;

    @FXML
    private TableColumn<TherapyProgramTM, String> ColName;

    @FXML
    private TableView<TherapyProgramTM> TblPrograms;

    @FXML
    private TextField TxtDuration;

    @FXML
    private TextField TxtFee;

    @FXML
    private TextField TxtId;

    @FXML
    private TextField TxtName;

    @FXML
    private AnchorPane root;

    TherapyProgramBO therapyProgramBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_PROGRAM);

    @FXML
    void BtnAddOnAction(ActionEvent event) throws Exception {

        String id = TxtId.getText();
        String name = TxtName.getText();
        String duration = TxtDuration.getText();
        String feeText = TxtFee.getText();

        String namePattern = "^[A-Za-z ]+$";

        boolean hasErrors = false;

        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        String errorStyle = "-fx-border-color: red; -fx-background-color: #F0FDF4;";

        String defaultStyle = "-fx-border-color: #86EFAC; -fx-background-color: #F0FDF4;";

        if (name.isEmpty() || !name.matches(namePattern)) {
            TxtName.setStyle(errorStyle);
            errorMessage.append("- Invalid program name\n");
            hasErrors = true;

        } else {
            TxtName.setStyle(defaultStyle);
        }

        if (duration.isEmpty()) {
            TxtDuration.setStyle(errorStyle);
            errorMessage.append("- Duration is required\n");
            hasErrors = true;
        } else {
            TxtDuration.setStyle(defaultStyle);
        }
        double fee = 0;

        try {
            fee = Double.parseDouble(feeText);
            TxtFee.setStyle(defaultStyle);

        } catch (NumberFormatException e) {
            TxtFee.setStyle(errorStyle);
            errorMessage.append("- Invalid fee amount\n");
            hasErrors = true;
        }

        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }

        boolean isSaved = therapyProgramBO.addTherapyProgram(new TherapyProgramDTO(id, name, duration, fee));

        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,
                    "Program added successfully!").show();

        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Failed to add program!").show();
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) throws Exception {

        String programId = TxtId.getText();

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Do you want to delete this program?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = therapyProgramBO.deleteTherapyProgram(programId);

            if (isDeleted) {
                refreshPage();

                new Alert(Alert.AlertType.INFORMATION,
                        "Program deleted successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR,
                        "Failed to delete program!").show();
            }
        }
    }

    @FXML
    void BtnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) throws Exception {

        String id = TxtId.getText();
        String name = TxtName.getText();
        String duration = TxtDuration.getText();
        String feeText = TxtFee.getText();

        String namePattern = "^[A-Za-z ]+$";
        boolean hasErrors = false;

        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");
        String errorStyle = "-fx-border-color: red; -fx-background-color: #F0FDF4;";
        String defaultStyle = "-fx-border-color: #86EFAC; -fx-background-color: #F0FDF4;";

        if (name.isEmpty() || !name.matches(namePattern)) {
            TxtName.setStyle(errorStyle);
            errorMessage.append("- Invalid program name\n");
            hasErrors = true;

        } else {
            TxtName.setStyle(defaultStyle);
        }

        if (duration.isEmpty()) {
            TxtDuration.setStyle(errorStyle);
            errorMessage.append("- Duration is required\n");
            hasErrors = true;

        } else {
            TxtDuration.setStyle(defaultStyle);
        }

        double fee = 0;

        try {
            fee = Double.parseDouble(feeText);
            TxtFee.setStyle(defaultStyle);

        } catch (NumberFormatException e) {
            TxtFee.setStyle(errorStyle);
            errorMessage.append("- Invalid fee amount\n");
            hasErrors = true;
        }

        if (hasErrors) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).show();
            return;
        }

        boolean isUpdated = therapyProgramBO.updateTherapyProgram(new TherapyProgramDTO(id, name, duration, fee));

        if (isUpdated) {
            refreshPage();

            new Alert(Alert.AlertType.INFORMATION,
                    "Program updated successfully!").show();

        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Failed to update program!").show();
        }
    }

    @FXML
    void TblProgramsOnAction(MouseEvent event) {

        TherapyProgramTM selectedProgram = TblPrograms.getSelectionModel().getSelectedItem();

        if (selectedProgram != null) {

            TxtId.setText(selectedProgram.getProgramId());
            TxtName.setText(selectedProgram.getProgramName());
            TxtDuration.setText(selectedProgram.getDuration());
            TxtFee.setText(String.valueOf(selectedProgram.getFee()));

            BtnAdd.setDisable(true);
            BtnUpdate.setDisable(false);
            BtnDelete.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ColId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        ColDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        ColFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws Exception {

        loadNextProgramId();
        loadTableData();

        BtnAdd.setDisable(false);
        BtnUpdate.setDisable(true);
        BtnDelete.setDisable(true);

        TxtName.clear();
        TxtDuration.clear();
        TxtFee.clear();

        String defaultStyle =
                "-fx-border-color: #86EFAC; " +
                        "-fx-background-color: #F0FDF4; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10;";

        TxtName.setStyle(defaultStyle);
        TxtDuration.setStyle(defaultStyle);
        TxtFee.setStyle(defaultStyle);
    }

    private void loadTableData() throws Exception {

        ArrayList<TherapyProgramDTO> programList = (ArrayList<TherapyProgramDTO>)
                        therapyProgramBO.getAllPrograms();

        ObservableList<TherapyProgramTM> tmList = FXCollections.observableArrayList();

        for (TherapyProgramDTO dto : programList) {

            TherapyProgramTM tm = new TherapyProgramTM(
                    dto.getId(),
                    dto.getProgramName(),
                    dto.getDuration(),
                    dto.getFee()
            );

            tmList.add(tm);
        }

        TblPrograms.setItems(tmList);
    }

    private void loadNextProgramId() {

        String nextId = therapyProgramBO.getNextProgramId();
        TxtId.setText(nextId);
    }
}
package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class UserController {

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnReset;

    @FXML
    private Button BtnUpdate;

    @FXML
    private ComboBox<?> CmbRole;

    @FXML
    private TableColumn<?, ?> ColEmail;

    @FXML
    private TableColumn<?, ?> ColId;

    @FXML
    private TableColumn<?, ?> ColName;

    @FXML
    private TableColumn<?, ?> ColRole;

    @FXML
    private TableColumn<?, ?> ColUsername;

    @FXML
    private Label LblId;

    @FXML
    private TableView<?> TblUsers;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtName;

    @FXML
    private PasswordField TxtPassword;

    @FXML
    private TextField TxtUsername;

    @FXML
    private AnchorPane root;

    @FXML
    void BtnAddOnAction(ActionEvent event) {

    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void BtnResetOnAction(ActionEvent event) {

    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void TblUserOnAction(MouseEvent event) {

    }

}

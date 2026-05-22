package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLayoutController implements Initializable {

    @FXML
    private Label lblWelcome;

    // Navigation Buttons
    @FXML
    private Button btnTherapists;

    @FXML
    private Button btnTherapyPrograms;

    // Report Buttons
    @FXML
    private Button btnTherapistPerformance;

    @FXML
    private Button btnSessionStatistics;

    @FXML
    private Button btnPatientTherapyHistory;

    // Charts
    @FXML
    private AreaChart<String, Number> therapyOverviewChart;

    @FXML
    private LineChart<String, Number> sessionChart;

    @FXML
    private CategoryAxis xAxisOverview;

    @FXML
    private NumberAxis yAxisOverview;

    @FXML
    private CategoryAxis xAxisSession;

    @FXML
    private NumberAxis yAxisSession;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadOverviewChart();
        loadSessionChart();
        showWelcomeMessage();
    }

    // =========================
    // BUTTON ACTIONS
    // =========================

    @FXML
    private AnchorPane contentPane;

    @FXML
    void btnTherapistsOnAction(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(
                    getClass().getResource("/lk/ijse/mental_health_therapy_center/therapist.fxml")
            );
            contentPane.getChildren().clear();
            contentPane.getChildren().add(view);

            // Make it fill the content pane
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Therapist page").show();
        }
    }

    @FXML
    void btnTherapyProgramsOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Therapy Programmes");
        alert.setHeaderText("Programme Management");
        alert.setContentText("Navigate to Therapy Programme Page");
        alert.showAndWait();
    }

    @FXML
    void btnTherapistPerformanceOnAction(ActionEvent event) {
        String report = """
                Therapist Performance Report
                
                • Dr. Amanda Silva - 42 Sessions
                • Dr. Kevin Perera - 38 Sessions
                • Dr. Nethmi Fernando - 35 Sessions
                
                Overall Therapist Performance is Excellent.
                """;

        showReportDialog("Therapist Performance", report);
    }

    @FXML
    void btnSessionStatisticsOnAction(ActionEvent event) {
        String report = """
                Therapy Session Statistics
                
                January   : 45 Sessions
                February  : 52 Sessions
                March     : 61 Sessions
                April     : 74 Sessions
                
                Session growth is increasing steadily.
                """;

        showReportDialog("Session Statistics", report);
    }

    @FXML
    void btnPatientTherapyHistoryOnAction(ActionEvent event) {
        String report = """
                Patient Therapy History
                
                • Patient P-001 completed Anxiety Therapy
                • Patient P-005 attending Depression Recovery
                • Patient P-010 completed Stress Management
                
                Therapy histories loaded successfully.
                """;

        showReportDialog("Patient Therapy History", report);
    }

    // =========================
    // CHARTS
    // =========================

    private void loadOverviewChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Therapy Programmes");

        series.getData().add(new XYChart.Data<>("Jan", 15));
        series.getData().add(new XYChart.Data<>("Feb", 22));
        series.getData().add(new XYChart.Data<>("Mar", 30));
        series.getData().add(new XYChart.Data<>("Apr", 40));
        series.getData().add(new XYChart.Data<>("May", 52));

        therapyOverviewChart.getData().add(series);
    }

    private void loadSessionChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sessions");

        series.getData().add(new XYChart.Data<>("Week 1", 10));
        series.getData().add(new XYChart.Data<>("Week 2", 18));
        series.getData().add(new XYChart.Data<>("Week 3", 25));
        series.getData().add(new XYChart.Data<>("Week 4", 32));

        sessionChart.getData().add(series);
    }

    // =========================
    // ALERTS & REPORTS
    // =========================

    private void showWelcomeMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome");
        alert.setHeaderText("Mental Health Therapy Center");
        alert.setContentText("""
                Welcome to the Admin Dashboard
                
                Manage therapists, programmes,
                therapy sessions, and analytics easily.
                
                Have a productive day!
                """);
        alert.showAndWait();
    }

    private void showReportDialog(String title, String content) {

        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }
}
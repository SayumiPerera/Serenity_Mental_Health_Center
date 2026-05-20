package lk.ijse.mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.mental_health_therapy_center.bo.BOFactory;
import lk.ijse.mental_health_therapy_center.bo.custom.PatientBO;
import lk.ijse.mental_health_therapy_center.bo.custom.PaymentBO;
import lk.ijse.mental_health_therapy_center.bo.custom.TherapistBO;
import lk.ijse.mental_health_therapy_center.bo.custom.TherapyProgramBO;
import lk.ijse.mental_health_therapy_center.bo.custom.TherapySessionBO;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

    public class AdminLayoutController implements Initializable {

        // ─── Header ────────────────────────────────────────────────────────────────
        @FXML
        private Label lblWelcome;

        // ─── Navigation buttons ────────────────────────────────────────────────────
        @FXML
        private Button btnTherapists;
        @FXML
        private Button btnTherapyPrograms;

        // ─── Report / action buttons ───────────────────────────────────────────────
        @FXML
        private Button btnTherapistPerformance;
        @FXML
        private Button btnSessionStatistics;
        @FXML
        private Button btnPatientTherapyHistory;

        // ─── Area chart (Therapy Overview) ────────────────────────────────────────
        @FXML
        private AreaChart<String, Number>  therapyOverviewChart;
        @FXML
        private CategoryAxis               xAxisOverview;
        @FXML
        private NumberAxis                 yAxisOverview;

        // ─── Line chart (Session Trend) ───────────────────────────────────────────
        @FXML
        private LineChart<String, Number>  sessionChart;
        @FXML
        private CategoryAxis               xAxisSession;
        @FXML
        private NumberAxis                 yAxisSession;

        // ─── Business Objects ─────────────────────────────────────────────────────
        private final PatientBO patientBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
        private final TherapyProgramBO programBO   = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_PROGRAM);
        private final TherapistBO therapistBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPIST);
        private final PaymentBO paymentBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
        private final TherapySessionBO sessionBO   = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_SESSION);

        // ══════════════════════════════════════════════════════════════════════════
        //  Initialise
        // ══════════════════════════════════════════════════════════════════════════

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            initCharts();
            loadOverviewChartData();
            loadSessionChartData();
        }

        // ══════════════════════════════════════════════════════════════════════════
        //  Navigation buttons
        // ══════════════════════════════════════════════════════════════════════════

        /** Opens the Therapist management form. */
        @FXML
        void btnTherapistsOnAction(ActionEvent event) {
            loadScene("/view/TherapistForm.fxml", "Therapist Management");
        }

        /** Opens the Therapy Programme management form. */
        @FXML
        void btnTherapyProgramsOnAction(ActionEvent event) {
            loadScene("/view/TherapyProgramForm.fxml", "Therapy Programme Management");
        }

        // ══════════════════════════════════════════════════════════════════════════
        //  Report / analytics buttons
        // ══════════════════════════════════════════════════════════════════════════

        /**
         * Shows a dialog listing each therapist's total session count,
         * then refreshes the overview area chart with the same data.
         */
        @FXML
        void btnTherapistPerformanceOnAction(ActionEvent event) {
            try {
                // getTherapistSessionCounts() returns Map<therapistName, sessionCount>
                Map<String, Integer> performance = therapistBO.getTherapistSessionCounts();

                StringBuilder sb = new StringBuilder("Therapist Performance Report\n\n");
                performance.forEach((name, count) ->
                        sb.append(String.format("%-30s : %d sessions%n", name, count)));

                showReport("Therapist Performance", sb.toString());
                updateOverviewChart("Therapist Sessions", performance);

            } catch (Exception e) {
                e.printStackTrace();
                showError("Failed to load therapist performance data.");
            }
        }

        /**
         * Shows a dialog with monthly therapy session counts,
         * then refreshes the session trend line chart.
         */
        @FXML
        void btnSessionStatisticsOnAction(ActionEvent event) {
            try {
                // getMonthlySessions() returns Map<monthLabel e.g."Jan 2025", count>
                Map<String, Integer> monthlySessions = sessionBO.getMonthlySessions();

                StringBuilder sb = new StringBuilder("Therapy Session Statistics\n\n");
                monthlySessions.forEach((month, count) ->
                        sb.append(String.format("%-15s : %d sessions%n", month, count)));

                showReport("Therapy Session Statistics", sb.toString());
                updateSessionChart("Sessions / Month", monthlySessions);

            } catch (Exception e) {
                e.printStackTrace();
                showError("Failed to load session statistics.");
            }
        }

        /**
         * Shows each patient's total session count in a report dialog.
         */
        @FXML
        void btnPatientTherapyHistoryOnAction(ActionEvent event) {
            try {
                // getPatientSessionCounts() returns Map<patientId, sessionCount>
                Map<String, Integer> sessionCounts = sessionBO.getPatientSessionCounts();

                StringBuilder sb = new StringBuilder("Patient Therapy History\n\n");

                for (Map.Entry<String, Integer> entry : sessionCounts.entrySet()) {
                    String patientName = patientBO.getPatientNameById(entry.getKey());
                    sb.append(String.format("%-30s : %d sessions%n",
                            patientName != null ? patientName : entry.getKey(),
                            entry.getValue()));
                }

                showReport("Patient Therapy History", sb.toString());

            } catch (Exception e) {
                e.printStackTrace();
                showError("Failed to load patient therapy history.");
            }
        }

        // ══════════════════════════════════════════════════════════════════════════
        //  Chart helpers
        // ══════════════════════════════════════════════════════════════════════════

        /** Seeds both charts with empty named series so JavaFX doesn't complain. */
        private void initCharts() {
            XYChart.Series<String, Number> overviewSeries = new XYChart.Series<>();
            overviewSeries.setName("Overview");
            therapyOverviewChart.getData().add(overviewSeries);

            XYChart.Series<String, Number> sessionSeries = new XYChart.Series<>();
            sessionSeries.setName("Sessions");
            sessionChart.getData().add(sessionSeries);
        }

        /**
         * Populates the area chart on startup using program enrollment data
         * (something real is always better than an empty chart at load time).
         */
        private void loadOverviewChartData() {
            try {
                Map<String, Integer> enrollments = programBO.getProgramEnrollmentCounts();
                updateOverviewChart("Enrollments", enrollments);
            } catch (Exception e) {
                // non-fatal — chart just stays empty
                e.printStackTrace();
            }
        }

        /**
         * Populates the line chart on startup using monthly session data.
         */
        private void loadSessionChartData() {
            try {
                Map<String, Integer> monthlySessions = sessionBO.getMonthlySessions();
                updateSessionChart("Sessions / Month", monthlySessions);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Replaces the first series in the area chart with new data.
         *
         * @param seriesName label shown in the chart legend
         * @param data       map of category-label → numeric value
         */
        @SuppressWarnings("unchecked")
        private void updateOverviewChart(String seriesName, Map<String, Integer> data) {
            XYChart.Series<String, Number> series =
                    (XYChart.Series<String, Number>) therapyOverviewChart.getData().get(0);
            series.setName(seriesName);
            series.getData().clear();
            data.forEach((label, value) ->
                    series.getData().add(new XYChart.Data<>(label, value)));
        }

        /**
         * Replaces the first series in the line chart with new data.
         *
         * @param seriesName label shown in the chart legend
         * @param data       map of category-label → numeric value
         */
        @SuppressWarnings("unchecked")
        private void updateSessionChart(String seriesName, Map<String, Integer> data) {
            XYChart.Series<String, Number> series =
                    (XYChart.Series<String, Number>) sessionChart.getData().get(0);
            series.setName(seriesName);
            series.getData().clear();
            data.forEach((label, value) ->
                    series.getData().add(new XYChart.Data<>(label, value)));
        }

        // ══════════════════════════════════════════════════════════════════════════
        //  UI utilities
        // ══════════════════════════════════════════════════════════════════════════

        /** Shows a scrollable text report in a modal dialog. */
        private void showReport(String title, String content) {
            TextArea textArea = new TextArea(content);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefSize(520, 360);

            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle(title);
            dialog.getDialogPane().setContent(textArea);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.showAndWait();
        }

        /** Shows a simple ERROR alert. */
        private void showError(String message) {
            new Alert(Alert.AlertType.ERROR, message).showAndWait();
        }

        /**
         * Loads an FXML form in a new window.
         *
         * @param fxmlPath  classpath path, e.g. "/view/TherapistForm.fxml"
         * @param title     window title
         */
        private void loadScene(String fxmlPath, String title) {
            try {
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(new Scene(
                        FXMLLoader.load(getClass().getResource(fxmlPath))));
                stage.centerOnScreen();
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                showError("Could not open: " + title);
            }
        }
    }


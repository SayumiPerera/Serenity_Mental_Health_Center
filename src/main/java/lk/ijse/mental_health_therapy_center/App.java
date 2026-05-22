package lk.ijse.mental_health_therapy_center;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.mental_health_therapy_center.bo.BOFactory;
import lk.ijse.mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    UserBO userBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void start(Stage stage) {

        try {

            FactoryConfiguration.getInstance().getSession();

            userBO.initializeDefaultUsers();

            Parent root = loadFXML("login");

            scene = new Scene(root);

            stage.setTitle("Mental Health Therapy Center");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource(
                        "/lk/ijse/mental_health_therapy_center/" + fxml + ".fxml"
                )
        );

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
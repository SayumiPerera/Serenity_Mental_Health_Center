package lk.ijse.mental_health_therapy_center;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.mental_health_therapy_center.bo.BOFactory;
import lk.ijse.mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;

import java.io.IOException;

public class HelloApplication extends Application {

    UserBO userBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FactoryConfiguration.getInstance().getSession();
        userBO.initializeDefaultUsers();

        Task<Scene> loadingTask = new Task<Scene>(){
            @Override
            protected Scene call() throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };
        loadingTask.setOnSucceeded(event ->{
            Scene value = loadingTask.getValue();

            stage.setTitle("Mental Health Therapy Center");
            stage.setScene(value);
        });
        new Thread(loadingTask).start();
    }
}


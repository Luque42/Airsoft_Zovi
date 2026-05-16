package es.Luque.AirsoftManager;

import es.Luque.AirsoftManager.controllers.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage test = null;
    public Button backButton;

    @Override
    public void start(Stage stage) throws IOException {
        test = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Airsoft Zovi");
        stage.setScene(scene);
        stage.show();
    }

    public void volverAlMenuPrincipal(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Stage nuevo = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Airsoft Zovi");
        nuevo.setScene(scene);

    }

}

package es.Luque.AirsoftManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage test = null;
    @Override
    public void start(Stage stage) throws IOException {
        test = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Airsoft Zovi");
        stage.setScene(scene);
        stage.show();
    }

}

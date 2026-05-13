package es.Luque.AirsoftManager.controllers;

import es.Luque.AirsoftManager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPartidaController {

    public Button crearPartidaButton;

    public void goToCrearPartida(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("crearPartida.fxml"));
        Stage nuevo = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Panel de administracion de Jugadores");
        nuevo.setScene(scene);
        nuevo.show();

    }
}

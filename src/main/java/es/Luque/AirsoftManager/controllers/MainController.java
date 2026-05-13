package es.Luque.AirsoftManager.controllers;

import es.Luque.AirsoftManager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    public Button btnAdminEquipos;
    public Button backButton;
    public Button inventaryButton;
    public Button adminJugadorButton;
    public Button crearPartidaButton;
    public Button adminPartidaButton;


    @FXML
    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goAdminEquipos(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-equipo.fxml"));
        Stage nuevo = (Stage) btnAdminEquipos.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Panel de administracion de equipos");
        nuevo.setScene(scene);

    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void volverAlMenuPrincipal(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Stage nuevo = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Airsoft Zovi");
        nuevo.setScene(scene);

    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goToReplica(ActionEvent actionEvent)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-replica.fxml"));
        Stage nuevo = (Stage) inventaryButton.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Panel de administracion de replicas");
        nuevo.setScene(scene);

    }

    public void goToAdminJugador(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-Jugador.fxml"));
        Stage nuevo = (Stage) adminJugadorButton.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Panel de administracion de Jugadores");
        nuevo.setScene(scene);
    }


    public void goToAdminPartida(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-partida.fxml"));
        Stage nuevo = (Stage) adminPartidaButton.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Panel de administracion de Jugadores");
        nuevo.setScene(scene);
    }
}

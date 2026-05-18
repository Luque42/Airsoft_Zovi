package es.Luque.AirsoftManager.controllers;

import es.Luque.AirsoftManager.Main;
import es.Luque.AirsoftManager.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminEquipoController {

    public Button backButton1;

    public void volverAlMenuPrincipal(ActionEvent actionEvent) throws IOException {
        Utils.backToMenu(backButton1);
    }
}

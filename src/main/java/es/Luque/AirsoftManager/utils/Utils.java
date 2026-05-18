package es.Luque.AirsoftManager.utils;

import es.Luque.AirsoftManager.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Utils {
    public static void errorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static Optional<ButtonType> mostrarDialogo(String titulo, String cabecera, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(mensaje);
        return alert.showAndWait();
    }

    public static boolean mostrarConfirmacion(String titulo, String mensaje) {
        Optional<ButtonType> result = mostrarDialogo(titulo, "", mensaje, Alert.AlertType.CONFIRMATION);
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static void backToMenu(Button buttonName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Stage nuevo = (Stage) buttonName.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Airsoft Zovi");
        nuevo.setScene(scene);
    }
}

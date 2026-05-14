package es.Luque.AirsoftManager.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
}

package es.Luque.AirsoftManager.controllers;

import es.Luque.AirsoftManager.DAO.PartidaDAO;
import es.Luque.AirsoftManager.Main;
import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Partida;
import es.Luque.AirsoftManager.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


public class AdminPartidaController {
    @FXML
    public Button crearPartidaButton;
    public Button btnRecargar;
    public Button EliminarPartidaButton;

    @FXML
    private ListView <Partida> partidasView = new ListView<>();

    public void recargar(ActionEvent actionEvent) throws SQLException {
        cargarLibros();
    }
    @FXML
    private void initialize() {
        configurarLista();
        try {
            ConnectionBD.getInstance().connect();
            cargarLibros();
            EliminarPartidaButton.setDisable(false);
            btnRecargar.setDisable(false);

        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de conexión", "No se ha podido conectar a la Base de datos: " + e.getMessage());
        }
    }


    public void goToCrearPartida(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("crearPartida.fxml"));
        Stage nuevo = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        nuevo.setTitle("Panel de administracion de Jugadores");
        nuevo.setScene(scene);
        nuevo.show();

    }
    private void cargarLibros() throws SQLException {
        List<Partida> libros = PartidaDAO.findAll();
        partidasView.setItems(FXCollections.observableList(libros));

    }


    private void configurarLista() {
        partidasView.setCellFactory(listView -> new ListCell<Partida>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            @Override
            protected void updateItem(Partida partida, boolean empty) {
                super.updateItem(partida, empty);
                if (empty || partida == null) {
                    setText(null);
                    return;
                }

                String fechaIni = partida.getFechaIni() != null
                        ? partida.getFechaIni().format(formatter)
                        : "Sin fecha inicio";

                String fechaFin = partida.getFechaFin() != null
                        ? partida.getFechaFin().format(formatter)
                        : "Sin fecha fin";

                String modo = partida.getModoDeJuego() != null
                        ? partida.getModoDeJuego().toString()
                        : "Sin modo";

                setText(fechaIni + " → " + fechaFin + " | " + modo);
            }

        });


//        partidasView.getSelectionModel().selectedItemProperty()
//                .addListener((observable, oldValue, newValue) -> {
//                    mostrarPartidaEnPanel(newValue);
//                });
//
//        partidasView.setPlaceholder(new Label("No hay partidas para mostrar"));
//        EliminarPartidaButton.disableProperty().bind(
//                partidasView.getSelectionModel().selectedItemProperty().isNull()
//        );
//        btnEditar.disableProperty().bind(
//                partidasView.getSelectionModel().selectedItemProperty().isNull()
//        );
//    }
//    private void mostrarLibroEnPanel(Partida partida) {
//        if (partida != null) {
//            detalleTituloLabel.setText(partida.getTitulo());
//            detalleAutorLabel.setText(partida.getAutor().getNombre());
//            detalleIdLabel.setText(String.valueOf(partida.getIdLibro()));
//            detalleIsbnLabel.setText(partida.getISBN());
//        } else {
//            detalleTituloLabel.setText(null);
//            detalleAutorLabel.setText(null);
//            detalleIdLabel.setText(null);
//            detalleIsbnLabel.setText(null);
//        }

    }


    @FXML
    private void eliminarPartida(ActionEvent actionEvent) throws SQLException {
        // Obtener el libro seleccionado
        Partida partida = partidasView.getSelectionModel().getSelectedItem();
        if (partida == null) {
            Utils.mostrarDialogo("Error", "No hay libro seleccionado", "Debe seleccionar un libro para eliminar", Alert.AlertType.ERROR);
            return;
        }
        // Vamos a confirmar que se quiera borrar
        Optional<ButtonType> respuesta = Utils.mostrarDialogo("Confirmar borrado", "Confirmar borrado de la partida", "¿Está seguro de que desea eliminar la partida?", Alert.AlertType.CONFIRMATION);
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            try {
                // borramos de la BD
                PartidaDAO.deletePartida(partida.getId());
                // Borramos de nuestra lista.
                partidasView.getItems().remove(partida);
                partidasView.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                Utils.mostrarDialogo("Error", "Error de base de datos", "No se ha podido borrar la partida en la BD: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        

    }
}

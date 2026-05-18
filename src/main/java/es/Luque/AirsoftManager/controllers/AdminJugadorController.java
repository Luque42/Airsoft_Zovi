package es.Luque.AirsoftManager.controllers;

import es.Luque.AirsoftManager.DAO.JugadorDAO;
import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Jugador;
import es.Luque.AirsoftManager.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AdminJugadorController {
    @FXML
    private TextField tfNombre, tfApellidos, tfDni, tfAltura, tfBuscar;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private ListView<Jugador> lvJugadores;
    @FXML
    private Button backButton1;

    private Jugador jugadorEditando = null;
    private List<Jugador> todosLosJugadores = null;

    @FXML
    private void initialize() {
        try {
            ConnectionBD.getInstance().connect();
            cargarJugadores();
        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de conexión", "No se ha podido conectar a la Base de datos: " + e.getMessage());
        }
    }

    private void cargarJugadores() throws SQLException {
        todosLosJugadores = JugadorDAO.findAllJugador();
        lvJugadores.setItems(FXCollections.observableList(todosLosJugadores));
        configurarListView();
    }

    private void configurarListView() {
        lvJugadores.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(Jugador jugador, boolean empty) {
                super.updateItem(jugador, empty);
                if (empty || jugador == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                HBox hbox = new HBox(10);
                hbox.setStyle("-fx-padding: 5;");
                Label info = new Label(String.format("[%d] %s %s | DNI: %s | Altura: %.2f m | Nac: %s",
                        jugador.getId(), jugador.getNombre(), jugador.getApellidos(),
                        jugador.getDni(), jugador.getAltura(), jugador.getfNacimiento()));
                info.setWrapText(true);
                hbox.getChildren().add(info);
                setGraphic(hbox);
            }
        });

        lvJugadores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                jugadorEditando = newVal;
                cargarDatosEnFormulario(newVal);
            }
        });
    }

    private void cargarDatosEnFormulario(Jugador jugador) {
        tfNombre.setText(jugador.getNombre());
        tfApellidos.setText(jugador.getApellidos());
        tfDni.setText(jugador.getDni());
        tfAltura.setText(String.valueOf(jugador.getAltura()));
        dpFechaNacimiento.setValue(jugador.getfNacimiento());
    }

    @FXML
    private void guardarJugador(ActionEvent actionEvent) {
        String nombre = tfNombre.getText().trim();
        String apellidos = tfApellidos.getText().trim();
        String dni = tfDni.getText().trim();
        String alturaStr = tfAltura.getText().trim();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();

        if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || alturaStr.isEmpty() || fechaNacimiento == null) {
            Utils.mostrarDialogo("Error", "Campos vacíos", "Por favor, rellene todos los campos", Alert.AlertType.ERROR);
            return;
        }

        try {
            double altura = Double.parseDouble(alturaStr);
            Jugador jugador = new Jugador(0, dni, nombre, apellidos, altura, fechaNacimiento);
            if (JugadorDAO.addJugador(jugador)) {
                Utils.mostrarDialogo("Éxito", "Jugador guardado", "El jugador ha sido guardado correctamente", Alert.AlertType.INFORMATION);
                limpiarFormulario();
                cargarJugadores();
            } else {
                Utils.mostrarDialogo("Error", "Jugador duplicado", "Ya existe un jugador con ese nombre", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            Utils.mostrarDialogo("Error", "Formato incorrecto", "La altura debe ser un número válido", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de base de datos", "No se pudo guardar el jugador: " + e.getMessage());
        }
    }

    @FXML
    private void editarJugador(ActionEvent actionEvent) {

    }

    @FXML
    private void eliminarJugador(ActionEvent actionEvent) {
        if (jugadorEditando == null) {
            Utils.mostrarDialogo("Error", "Sin selección", "Seleccione un jugador para eliminar", Alert.AlertType.ERROR);
            return;
        }

        Optional<ButtonType> respuesta = Utils.mostrarDialogo("Confirmar", "¿Está seguro de que desea eliminar este jugador?", "", Alert.AlertType.CONFIRMATION);
        if (!respuesta.isPresent() || respuesta.get() != ButtonType.OK) {
            return;
        }

        try {
            JugadorDAO.deleteJugadorById(jugadorEditando.getId());
            Utils.mostrarDialogo("Éxito", "Jugador eliminado", "El jugador ha sido eliminado correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
            cargarJugadores();
            jugadorEditando = null;
        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de base de datos", "No se pudo eliminar el jugador: " + e.getMessage());
        }
    }

    @FXML
    private void buscarJugadores(ActionEvent actionEvent) {
        String patron = tfBuscar.getText().trim();
        
        if (patron.isEmpty()) {
            // Si está vacío, mostrar todos los jugadores
            lvJugadores.setItems(FXCollections.observableList(todosLosJugadores));
            configurarListView();
            return;
        }

        try {
            Pattern pattern = Pattern.compile(patron, Pattern.CASE_INSENSITIVE);
            List<Jugador> jugadoresFiltrados = todosLosJugadores.stream()
                    .filter(j -> pattern.matcher(j.getNombre()).find() ||
                            pattern.matcher(j.getApellidos()).find() ||
                            pattern.matcher(j.getDni()).find())
                    .collect(Collectors.toList());

            if (jugadoresFiltrados.isEmpty()) {
                Utils.mostrarDialogo("Información", "Sin resultados", 
                        "No se encontraron jugadores que coincidan con: " + patron, 
                        Alert.AlertType.INFORMATION);
                lvJugadores.setItems(FXCollections.observableList(todosLosJugadores));
            } else {
                lvJugadores.setItems(FXCollections.observableList(jugadoresFiltrados));
            }
            configurarListView();
        } catch (Exception e) {
            Utils.mostrarDialogo("Error", "Expresión regular inválida", 
                    "La expresión regular tiene un error: " + e.getMessage(), 
                    Alert.AlertType.ERROR);
        }
    }

    private void limpiarFormulario() {
        tfNombre.clear();
        tfApellidos.clear();
        tfDni.clear();
        tfAltura.clear();
        dpFechaNacimiento.setValue(null);
    }

    @FXML
    private void volverAlMenuPrincipal(ActionEvent actionEvent) throws IOException {
        Utils.backToMenu(backButton1);
    }
}

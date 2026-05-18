package es.Luque.AirsoftManager.controllers;

import es.Luque.AirsoftManager.DAO.ReplicaDAO;
import es.Luque.AirsoftManager.Main;
import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Replica;
import es.Luque.AirsoftManager.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AdminReplicaController {
    @FXML
    private TextField tfNombre, tfSerie, tfBuscar;
    @FXML
    private Spinner<Integer> spinnerPotencia;
    @FXML
    private ListView<Replica> lvReplicas;
    @FXML
    private Button backButton1, backButton, btnGuardar, btnEditar, btnEliminar, btnBuscar;

    private Replica replicaEditando = null;

    @FXML
    private void initialize() {
        try {
            ConnectionBD.getInstance().connect();
            configurarSpinner();
            cargarReplicas();
        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de conexión", "No se ha podido conectar a la Base de datos: " + e.getMessage());
        }
    }

    private void configurarSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 1);
        spinnerPotencia.setValueFactory(valueFactory);
    }

    private void cargarReplicas() throws SQLException {
        List<Replica> replicas = ReplicaDAO.findAll();
        lvReplicas.setItems(FXCollections.observableList(replicas));
        configurarListView();
    }

    private void configurarListView() {
        lvReplicas.setCellFactory(listView -> new ListCell<Replica>() {
            @Override
            protected void updateItem(Replica replica, boolean empty) {
                super.updateItem(replica, empty);
                if (empty || replica == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                HBox hbox = new HBox(10);
                hbox.setStyle("-fx-padding: 5;");
                Label info = new Label(String.format("[%d] %s | Serie: %s | Potencia: %d J",
                        replica.getId(), replica.getNombre(), replica.getnSerie(), replica.getPotencia()));
                info.setWrapText(true);
                hbox.getChildren().add(info);
                setGraphic(hbox);
            }
        });

        lvReplicas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                replicaEditando = newVal;
            }
        });
    }

    @FXML
    private void guardarReplica(ActionEvent actionEvent) {
        String nombre = tfNombre.getText().trim();
        String serie = tfSerie.getText().trim();
        int potencia = spinnerPotencia.getValue();

        if (nombre.isEmpty() || serie.isEmpty()) {
            Utils.mostrarDialogo("Error", "Campos vacíos", "Por favor, rellene todos los campos", Alert.AlertType.ERROR);
            return;
        }

        try {
            Replica replica = new Replica(0, nombre, serie, potencia);
            if (ReplicaDAO.addReplica(replica)) {
                Utils.mostrarDialogo("Éxito", "Réplica guardada", "La réplica ha sido guardada correctamente", Alert.AlertType.INFORMATION);
                tfNombre.clear();
                tfSerie.clear();
                spinnerPotencia.getValueFactory().setValue(1);
                cargarReplicas();
            } else {
                Utils.mostrarDialogo("Error", "Réplica duplicada", "Ya existe una réplica con ese nombre", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de base de datos", "No se pudo guardar la réplica: " + e.getMessage());
        }
    }

    @FXML
    private void editarReplica(ActionEvent actionEvent) {
        if (replicaEditando == null) {
            Utils.mostrarDialogo("Error", "Sin selección", "Seleccione una réplica para editar", Alert.AlertType.ERROR);
            return;
        }

        boolean confirmado = Utils.mostrarConfirmacion("Editar", "¿Confirma la edición?");
        if (!confirmado) return;

        try {
            String nombre = tfNombre.getText().trim();
            String serie = tfSerie.getText().trim();
            int potencia = spinnerPotencia.getValue();

            if (nombre.isEmpty() || serie.isEmpty()) {
                Utils.mostrarDialogo("Error", "Campos vacíos", "Por favor, rellene todos los campos", Alert.AlertType.ERROR);
                return;
            }

            Replica replicaActualizada = new Replica(replicaEditando.getId(), nombre, serie, potencia);
            ReplicaDAO.updateReplica(replicaActualizada);

            Utils.mostrarDialogo("Éxito", "Réplica actualizada", "La réplica ha sido actualizada correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
            cargarReplicas();
            replicaEditando = null;
        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de base de datos", "No se pudo actualizar la réplica: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarReplica(ActionEvent actionEvent) {
        if (replicaEditando == null) {
            Utils.mostrarDialogo("Error", "Sin selección", "Seleccione una réplica para eliminar", Alert.AlertType.ERROR);
            return;
        }

        Optional<ButtonType> respuesta = Utils.mostrarDialogo("Confirmar", "¿Está seguro de que desea eliminar esta réplica?", "", Alert.AlertType.CONFIRMATION);
        if (!respuesta.isPresent() || respuesta.get() != ButtonType.OK) {
            return;
        }

        try {
            ReplicaDAO.deleteReplica(replicaEditando.getId());
            Utils.mostrarDialogo("Éxito", "Réplica eliminada", "La réplica ha sido eliminada correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
            cargarReplicas();
            replicaEditando = null;
        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de base de datos", "No se pudo eliminar la réplica: " + e.getMessage());
        }
    }

    @FXML
    private void buscarReplica(ActionEvent actionEvent) {
        String termino = tfBuscar.getText().trim();
        if (termino.isEmpty()) {
            try {
                cargarReplicas();
            } catch (SQLException e) {
                Utils.errorAlert("Error", "Error de base de datos", e.getMessage());
            }
            return;
        }

        try {
            Replica replica = ReplicaDAO.findByName(termino);
            if (replica != null) {
                lvReplicas.setItems(FXCollections.observableList(List.of(replica)));
                lvReplicas.setCellFactory(listView -> new ListCell<Replica>() {
                    @Override
                    protected void updateItem(Replica r, boolean empty) {
                        super.updateItem(r, empty);
                        if (empty || r == null) {
                            setText(null);
                            setGraphic(null);
                            return;
                        }
                        HBox hbox = new HBox(10);
                        hbox.setStyle("-fx-padding: 5;");
                        Label info = new Label(String.format("[%d] %s | Serie: %s | Potencia: %d J",
                                r.getId(), r.getNombre(), r.getnSerie(), r.getPotencia()));
                        info.setWrapText(true);
                        hbox.getChildren().add(info);
                        setGraphic(hbox);
                    }
                });
            } else {
                Utils.mostrarDialogo("Información", "No encontrada", "No se encontró réplica con ese nombre", Alert.AlertType.INFORMATION);
                cargarReplicas();
            }
        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error de base de datos", e.getMessage());
        }
    }

    private void limpiarFormulario() {
        tfNombre.clear();
        tfSerie.clear();
        spinnerPotencia.getValueFactory().setValue(1);
    }

    @FXML
    private void volverAlMenuPrincipal(ActionEvent actionEvent) throws IOException {
        Utils.backToMenu(backButton1);
    }


}

package es.Luque.AirsoftManager.controllers;

import es.Luque.AirsoftManager.DAO.CampoDAO;
import es.Luque.AirsoftManager.DAO.PartidaDAO;
import es.Luque.AirsoftManager.model.Campo;
import es.Luque.AirsoftManager.model.CampoExterior;
import es.Luque.AirsoftManager.model.CampoInterior;
import es.Luque.AirsoftManager.model.Partida;
import es.Luque.AirsoftManager.model.enums.ModoJuego;
import es.Luque.AirsoftManager.model.enums.TamanoCampo;
import es.Luque.AirsoftManager.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CrearPartidaController {

    @FXML
    public DatePicker dpFechaIni;
    @FXML
    public DatePicker dpFechaFin;
    @FXML
    public Spinner<Integer> spinHoraIni;
    @FXML
    public Spinner<Integer> spinMinutoIni;
    @FXML
    public Spinner<Integer> spinHoraFin;
    @FXML
    public Spinner<Integer> spinMinutoFin;
    @FXML
    public ComboBox<ModoJuego> cmbModoDeJuego;
    @FXML
    public ComboBox<Campo> cmbCampo;
    @FXML
    public Label tituloFormularioLabel;
    @FXML
    public Button btnGuardar;
    private Partida partidaAEditar;

    public void inicializar(Partida partida) {
        this.partidaAEditar = partida;
        configurarSpinners();
        cargarCampos();
        cargarModos();
        configurarListeners();
        if (partida != null) {
            dpFechaIni.setValue(partida.getFechaIni().toLocalDate());
            spinHoraIni.getValueFactory().setValue(partida.getFechaIni().getHour());
            spinMinutoIni.getValueFactory().setValue(partida.getFechaIni().getMinute());

            dpFechaFin.setValue(partida.getFechaFin().toLocalDate());
            spinHoraFin.getValueFactory().setValue(partida.getFechaFin().getHour());
            spinMinutoFin.getValueFactory().setValue(partida.getFechaFin().getMinute());

            cmbModoDeJuego.setValue(partida.getModoDeJuego());
            tituloFormularioLabel.setText("Editar partida");
        } else {
            dpFechaIni.setValue(null);
            spinHoraIni.getValueFactory().setValue(0);
            spinMinutoIni.getValueFactory().setValue(0);

            dpFechaFin.setValue(null);
            spinHoraFin.getValueFactory().setValue(23);
            spinMinutoFin.getValueFactory().setValue(59);

            cmbModoDeJuego.setValue(null);
            cmbCampo.setValue(null);
            tituloFormularioLabel.setText("Nueva partida");
        }
        actualizarBotonGuardar();
    }

    private void cargarCampos() {
        try {
            List<Campo> interiores = CampoDAO.findAllInterior();
            List<Campo> exteriores = CampoDAO.findAllExterior();

            List<Campo> items = new ArrayList<>();
            // cabecera interior
            if (!interiores.isEmpty()) {
                items.add(new CampoInterior(-1, "-- Campos Interior --", TamanoCampo.MEDIANO));
                items.addAll(interiores);
            }
            // cabecera exterior
            if (!exteriores.isEmpty()) {
                items.add(new CampoExterior(-2, "-- Campos Exterior --"));
                items.addAll(exteriores);
            }

            cmbCampo.setItems(FXCollections.observableArrayList(items));

            // Mostrar solo el nombre en la lista y en el botón; marcar cabeceras con estilo y no seleccionables
            cmbCampo.setCellFactory(lv -> new ListCell<Campo>() {
                @Override
                protected void updateItem(Campo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setDisable(false);
                        setStyle("");
                    } else if (item.getId() <= 0) { // header
                        setText(item.getNombre());
                        setDisable(true);
                        setStyle("-fx-font-weight: bold; -fx-background-color: -fx-control-inner-background;");
                    } else {
                        setText(item.getNombre());
                        setDisable(false);
                        setStyle("");
                    }
                }
            });

            cmbCampo.setButtonCell(new ListCell<Campo>() {
                @Override
                protected void updateItem(Campo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getId() <= 0) {
                        setText(null);
                    } else {
                        setText(item.getNombre());
                    }
                }
            });

        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error al cargar campos", "No se han podido obtener los campos: " + e.getMessage());
        }
    }

    private void configurarSpinners() {
        // Configurar spinner de hora inicio
        spinHoraIni.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        spinHoraIni.setPrefWidth(60);

        // Configurar spinner de minuto inicio
        spinMinutoIni.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        spinMinutoIni.setPrefWidth(60);

        // Configurar spinner de hora fin
        spinHoraFin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 23));
        spinHoraFin.setPrefWidth(60);

        // Configurar spinner de minuto fin
        spinMinutoFin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 59));
        spinMinutoFin.setPrefWidth(60);
    }

    @FXML
    private void guardarPartida(ActionEvent actionEvent) {
        try {
            LocalDateTime fechaIni = dpFechaIni.getValue() != null
                    ? dpFechaIni.getValue().atTime(
                    spinHoraIni.getValue(),
                    spinMinutoIni.getValue())
                    : null;
            LocalDateTime fechaFin = dpFechaFin.getValue() != null
                    ? dpFechaFin.getValue().atTime(
                    spinHoraFin.getValue(),
                    spinMinutoFin.getValue())
                    : null;
            ModoJuego modoDeJuego = cmbModoDeJuego.getValue();
            Campo campoSeleccionado = cmbCampo.getValue();

            // prevenir cabeceras
            if (campoSeleccionado == null || campoSeleccionado.getId() <= 0) {
                Utils.errorAlert("Campo inválido", "Seleccione un campo", "Debe seleccionar un campo válido (no una categoría)");
                return;
            }
            if (this.partidaAEditar == null) {
                Partida partidaNueva = new Partida(0, modoDeJuego, fechaIni, fechaFin);
                boolean added = PartidaDAO.addPartida(partidaNueva);
                if (!added) {
                    Utils.errorAlert("Error", "Error al guardar", "No se ha podido guardar la partida");
                } else {
                    Stage stage = (Stage) dpFechaIni.getScene().getWindow();
                    stage.close();
                }
            } else {
                partidaAEditar.setModoDeJuego(modoDeJuego);
                partidaAEditar.setFechaIni(fechaIni);
                partidaAEditar.setFechaFin(fechaFin);
                try {
                    PartidaDAO.updatePartida(partidaAEditar);
                    Stage stage = (Stage) dpFechaIni.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    Utils.errorAlert("Error", "Error al editar", "No se ha podido editar la partida: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            Utils.errorAlert("Error", "Error al realizar la operación", "No se ha podido conectar a la Base de datos: " + e.getMessage());
        }
    }

    private void cargarModos() {
        cmbModoDeJuego.setItems(FXCollections.observableArrayList(ModoJuego.values()));
    }

    private void configurarListeners() {
        dpFechaIni.valueProperty().addListener((obs, old, newVal) -> actualizarBotonGuardar());
        dpFechaFin.valueProperty().addListener((obs, old, newVal) -> actualizarBotonGuardar());
        spinHoraIni.valueProperty().addListener((obs, old, newVal) -> actualizarBotonGuardar());
        spinMinutoIni.valueProperty().addListener((obs, old, newVal) -> actualizarBotonGuardar());
        spinHoraFin.valueProperty().addListener((obs, old, newVal) -> actualizarBotonGuardar());
        spinMinutoFin.valueProperty().addListener((obs, old, newVal) -> actualizarBotonGuardar());
        cmbModoDeJuego.valueProperty().addListener((obs, old, newVal) -> actualizarBotonGuardar());
        cmbCampo.valueProperty().addListener((obs, old, newVal) -> actualizarBotonGuardar());
    }

    public void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) dpFechaIni.getScene().getWindow();
        stage.close();
    }

    private void actualizarBotonGuardar() {
        btnGuardar.setDisable(!datosValidos());
    }

    private boolean datosValidos() {
        if (dpFechaIni.getValue() == null || dpFechaFin.getValue() == null || cmbModoDeJuego.getValue() == null || cmbCampo.getValue() == null) {
            return false;
        }

        Integer horaIni = spinHoraIni.getValue();
        Integer minutoIni = spinMinutoIni.getValue();
        Integer horaFin = spinHoraFin.getValue();
        Integer minutoFin = spinMinutoFin.getValue();

        if (horaIni == null || minutoIni == null || horaFin == null || minutoFin == null) {
            return false;
        }

        LocalDateTime fechaHoraIni = dpFechaIni.getValue().atTime(horaIni, minutoIni);
        LocalDateTime fechaHoraFin = dpFechaFin.getValue().atTime(horaFin, minutoFin);

        // El valor seleccionado no debe ser una cabecera (id <= 0)
        Campo selected = cmbCampo.getValue();
        if (selected == null || selected.getId() <= 0) return false;

        return fechaHoraIni.isBefore(fechaHoraFin);
    }
}

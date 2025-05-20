package view;

import DAO.ViajeDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Viajes;

public class EditarViajeF {

    @FXML
    private TextField txtDestino;
    @FXML
    private DatePicker dpFechaSalida;
    @FXML
    private DatePicker dpFechaRegreso;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtPlazas;

    private Viajes viaje;
    private boolean modoEdicion;

    // Método para configurar el modo de edición
    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }

    // Método para cargar los datos del viaje seleccionado
    public void cargarDatos(Viajes viaje) {
        this.viaje = viaje;
        if (viaje != null) {
            txtDestino.setText(viaje.getDestino());
            dpFechaSalida.setValue(viaje.getFecha_salida());
            dpFechaRegreso.setValue(viaje.getFecha_regreso());
            txtPrecio.setText(String.valueOf(viaje.getPrecio()));
            txtPlazas.setText(String.valueOf(viaje.getPlazas()));
        }
    }

    public void setDatosViaje(Viajes viaje) {
        this.viaje = viaje;
        if (viaje != null) {
            txtDestino.setText(viaje.getDestino());
            dpFechaSalida.setValue(viaje.getFecha_salida());
            dpFechaRegreso.setValue(viaje.getFecha_regreso());
            txtPrecio.setText(String.valueOf(viaje.getPrecio()));
            txtPlazas.setText(String.valueOf(viaje.getPlazas()));
        }
    }

    @FXML
    private void handleGuardar() {
        try {
            if (viaje == null) {
                viaje = new Viajes();
            }
            viaje.setDestino(txtDestino.getText().trim());
            viaje.setFecha_salida(dpFechaSalida.getValue());
            viaje.setFecha_regreso(dpFechaRegreso.getValue());
            viaje.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            viaje.setPlazas(Integer.parseInt(txtPlazas.getText().trim()));

            boolean operacionExitosa = false;

            if (modoEdicion) {
                // Modo edición: actualizar un viaje existente
                operacionExitosa = ViajeDAO.updateViaje(viaje);
            } else {
                // Modo creación: insertar un nuevo viaje
                Viajes resultado = ViajeDAO.insertViaje(viaje);
                operacionExitosa = (resultado != null);
            }

            if (operacionExitosa) {
                mostrarAlerta("Éxito", "El viaje ha sido guardado correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar el viaje.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Datos inválidos. Por favor, verifica los campos.");
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtDestino.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
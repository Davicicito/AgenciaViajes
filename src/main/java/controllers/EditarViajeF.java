package controllers;

import DAO.ViajeDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Viajes;

public class EditarViajeF {

    // Campos del formulario vinculados desde el archivo FXML
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

    // Objeto Viaje que se va a editar o crear
    private Viajes viaje;

    // Indica si estamos en modo edición (true) o creación (false)
    private boolean modoEdicion;

    // Establece si se está editando un viaje o creando uno nuevo
    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }

    // Carga los datos de un viaje en los campos del formulario
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

    // Método alternativo para cargar datos del viaje (parecido al anterior)
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

    // Maneja el evento de guardar el viaje (crear o actualizar)
    @FXML
    private void handleGuardar() {
        try {
            if (viaje == null) {
                viaje = new Viajes(); // Crear nuevo objeto si no existe
            }


            // Obtener datos desde los campos del formulario
            viaje.setDestino(txtDestino.getText().trim());
            viaje.setFecha_salida(dpFechaSalida.getValue());
            viaje.setFecha_regreso(dpFechaRegreso.getValue());
            viaje.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            viaje.setPlazas(Integer.parseInt(txtPlazas.getText().trim()));

            // Validación de fechas
            if (viaje.getFecha_regreso().isBefore(viaje.getFecha_salida())) {
                mostrarAlerta("Error", "La fecha de regreso no puede ser anterior a la fecha de salida.");
                return;
            }
            if (viaje.getFecha_salida().isAfter(viaje.getFecha_regreso())) {
                mostrarAlerta("Error", "La fecha de salida no puede ser posterior a la fecha de regreso.");
                return;
            }

            boolean operacionExitosa = false;

            if (modoEdicion) {
                // Si estamos en modo edición, actualizar viaje
                operacionExitosa = ViajeDAO.updateViaje(viaje);
            } else {
                // Si estamos en modo creación, insertar nuevo viaje
                Viajes resultado = ViajeDAO.insertViaje(viaje);
                operacionExitosa = (resultado != null);
            }

            // Mostrar mensaje según resultado
            if (operacionExitosa) {
                mostrarAlerta("Éxito", "El viaje ha sido guardado correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar el viaje.");
            }
        } catch (Exception e) {
            // Captura errores como campos vacíos, tipos inválidos, etc.
            mostrarAlerta("Error", "Datos inválidos. Por favor, verifica los campos.");
        }
    }

    // Maneja el evento de cancelar: cierra la ventana sin guardar cambios
    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    // Cierra la ventana actual del formulario
    private void cerrarVentana() {
        Stage stage = (Stage) txtDestino.getScene().getWindow();
        stage.close();
    }

    // Muestra una alerta de información al usuario
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

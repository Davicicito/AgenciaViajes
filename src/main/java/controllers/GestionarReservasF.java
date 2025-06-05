package controllers;

import DAO.ReservaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Reservas;
import java.time.LocalDate;
import java.util.List;

public class GestionarReservasF {

    @FXML
    private TableView<Reservas> tableReservas;
    @FXML
    private TableColumn<Reservas, Integer> colIDReserva;
    @FXML
    private TableColumn<Reservas, Integer> colIDCliente;
    @FXML
    private TableColumn<Reservas, Integer> colIDViaje;
    @FXML
    private TableColumn<Reservas, String> colEstado;
    @FXML
    private TableColumn<Reservas, LocalDate> colFechaSalida;
    @FXML
    private TableColumn<Reservas, LocalDate> colFechaRegreso;
    @FXML
    private TableColumn<Reservas, String> colDestino;
    @FXML
    private TableColumn<Reservas, Double> colPrecio;
    @FXML
    private TableColumn<Reservas, Integer> colIDAgente;

    private String codigoEmpleado;

    @FXML
    public void initialize() {
        colIDReserva.setCellValueFactory(new PropertyValueFactory<>("ID_Reserva"));
        colIDCliente.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        colIDAgente.setCellValueFactory(new PropertyValueFactory<>("Codigo_Empleado"));
        colIDViaje.setCellValueFactory(new PropertyValueFactory<>("ID_Viaje"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("Fecha_salida"));
        colFechaRegreso.setCellValueFactory(new PropertyValueFactory<>("Fecha_regreso"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("Destino"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));

        cargarReservasConDetalles();
    }

    // Asigna el Código de Empleado y recarga las reservas correspondientes
    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
        cargarReservasConDetalles();
    }

    private void cargarReservasConDetalles() {
        if (codigoEmpleado != null && !codigoEmpleado.isEmpty()) {
            List<Reservas> reservas = ReservaDAO.findReservasByAgente(codigoEmpleado); // Busca las reservas por Código de Empleado
            tableReservas.getItems().setAll(reservas); // Muestra las reservas en la tabla
        } else {
            tableReservas.getItems().clear(); // Limpia la tabla si no hay Código de Empleado asignado
        }
    }

    // Permite editar la reserva seleccionada (fecha salida, fecha regreso y estado)
    @FXML
    private void handleEditarReserva() {
        Reservas reservaSeleccionada = tableReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Por favor, selecciona una reserva para editar.");
            return;
        }

        // Crear formulario con campos editables
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Reserva");
        dialog.setHeaderText("Modifica los detalles de la reserva");

        DatePicker datePickerSalida = new DatePicker(reservaSeleccionada.getFecha_salida());
        DatePicker datePickerRegreso = new DatePicker(reservaSeleccionada.getFecha_regreso());
        ComboBox<String> comboBoxEstado = new ComboBox<>();
        comboBoxEstado.getItems().addAll("Aceptada", "Pendiente", "Cancelada");
        comboBoxEstado.setValue(reservaSeleccionada.getEstado());

        // Mostrar formulario en un GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Fecha Salida:"), 0, 0);
        grid.add(datePickerSalida, 1, 0);
        grid.add(new Label("Fecha Regreso:"), 0, 1);
        grid.add(datePickerRegreso, 1, 1);
        grid.add(new Label("Estado:"), 0, 2);
        grid.add(comboBoxEstado, 1, 2);
        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Si el usuario confirma, se actualiza la reserva
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                reservaSeleccionada.setFecha_salida(datePickerSalida.getValue());
                reservaSeleccionada.setFecha_regreso(datePickerRegreso.getValue());
                reservaSeleccionada.setEstado(comboBoxEstado.getValue());

                boolean actualizado = ReservaDAO.updateReserva(reservaSeleccionada);
                if (actualizado) {
                    tableReservas.refresh(); // Refresca la tabla visualmente
                    mostrarAlerta("Éxito", "La reserva ha sido actualizada correctamente.");
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar la reserva.");
                }
            }
        });
    }

    // Elimina la reserva seleccionada tras confirmar con el usuario
    @FXML
    private void handleEliminarReserva() {
        Reservas reservaSeleccionada = tableReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Por favor, selecciona una reserva para eliminar.");
            return;
        }

        // Confirmación antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar esta reserva?");
        if (confirmacion.showAndWait().orElse(null) != ButtonType.OK) {
            return;
        }

        // Eliminación real
        boolean eliminado = ReservaDAO.deleteReserva(reservaSeleccionada.getID_Reserva());
        if (eliminado) {
            tableReservas.getItems().remove(reservaSeleccionada); // Quita la reserva de la tabla
            mostrarAlerta("Éxito", "La reserva ha sido eliminada correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo eliminar la reserva.");
        }
    }

    // Cierra la ventana actual de gestión de reservas
    @FXML
    private void handleVolver() {
        Stage stage = (Stage) tableReservas.getScene().getWindow();
        stage.close();
    }

    // Muestra una alerta informativa con el mensaje proporcionado
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}


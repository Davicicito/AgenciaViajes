package controllers;

import DAO.ClienteDAO;
import DAO.ReservaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Reservas;

import java.time.LocalDate;
import java.util.List;

public class ReservasClienteF {

    private String dniCliente; // Variable para almacenar el DNI del cliente


    @FXML
    private TableView<Reservas> tableReservas;
    @FXML
    private TableColumn<Reservas, String> colDestino;
    @FXML
    private TableColumn<Reservas, LocalDate> colFechaSalida;
    @FXML
    private TableColumn<Reservas, LocalDate> colFechaRegreso;
    @FXML
    private TableColumn<Reservas, String> colEstado;

    @FXML
    public void initialize() {
        colDestino.setCellValueFactory(new PropertyValueFactory<>("Destino"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("Fecha_salida"));
        colFechaRegreso.setCellValueFactory(new PropertyValueFactory<>("Fecha_regreso"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
    }

    // Establece el ID del cliente y carga sus reservas
    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
        cargarReservas(dniCliente);
    }

    // Carga las reservas del cliente en la tabla
    private void cargarReservas(String dniCliente) {
        List<Reservas> reservas = ReservaDAO.findByClienteDNI(dniCliente);
        tableReservas.getItems().setAll(reservas);
    }

    // Cancela la reserva seleccionada
    @FXML
    private void handleCancelarReserva() {
        Reservas reservaSeleccionada = tableReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            boolean eliminado = ReservaDAO.deleteReserva(reservaSeleccionada.getID_Reserva());
            if (eliminado) {
                cargarReservas(dniCliente); // Pasa el dniCliente como argumento
                mostrarMensaje("Reserva cancelada con éxito.");
            } else {
                mostrarMensaje("Error al cancelar la reserva.");
            }
        } else {
            mostrarMensaje("Selecciona una reserva para cancelar.");
        }
    }

    // Abre un diálogo para modificar la reserva seleccionada y actualiza la tabla tras cambios
    @FXML
    private void handleModificarReserva() {
        Reservas reservaSeleccionada = tableReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Por favor, selecciona una reserva para modificar.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modificar Reserva");
        dialog.setHeaderText("Edita los detalles de la reserva seleccionada");

        DatePicker datePickerSalida = new DatePicker(reservaSeleccionada.getFecha_salida());
        DatePicker datePickerRegreso = new DatePicker(reservaSeleccionada.getFecha_regreso());
        ComboBox<String> comboBoxEstado = new ComboBox<>();
        comboBoxEstado.getItems().addAll("Aceptada", "Pendiente", "Cancelada");
        comboBoxEstado.setValue(reservaSeleccionada.getEstado());

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
                // Validación de fechas
                if (datePickerRegreso.getValue().isBefore(datePickerSalida.getValue())) {
                    mostrarAlerta("Error", "La fecha de regreso no puede ser anterior a la fecha de salida.");
                    return;
                }
                if (datePickerSalida.getValue().isAfter(datePickerRegreso.getValue())) {
                    mostrarAlerta("Error", "La fecha de salida no puede ser posterior a la fecha de regreso.");
                    return;
                }

                reservaSeleccionada.setFecha_salida(datePickerSalida.getValue());
                reservaSeleccionada.setFecha_regreso(datePickerRegreso.getValue());
                reservaSeleccionada.setEstado(comboBoxEstado.getValue());

               if (reservaSeleccionada.getCliente() == null) {
                   reservaSeleccionada.setCliente(ClienteDAO.findByDNI(dniCliente));
                   if (reservaSeleccionada.getCliente() == null) {
                       mostrarAlerta("Error", "No se encontró el cliente con ese DNI.");
                       return;
                   }
               }

                boolean actualizado = ReservaDAO.updateReserva(reservaSeleccionada);
                if (actualizado) {
                    tableReservas.refresh();
                    mostrarAlerta("Éxito", "La reserva ha sido actualizada correctamente.");
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar la reserva.");
                }
            }
        });
    }

    // Muestra un mensaje informativo simple
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Muestra una alerta con título y mensaje
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Cierra la ventana actual
    @FXML
    private void handleVolver() {
        Stage stage = (Stage) tableReservas.getScene().getWindow();
        stage.close();
    }


}
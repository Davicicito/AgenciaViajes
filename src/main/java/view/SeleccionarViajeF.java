package view;

import DAO.ReservaDAO;
import DAO.ViajeDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Reservas;
import model.Viajes;

import java.time.LocalDate;
import java.util.List;

public class SeleccionarViajeF {

    @FXML
    private TableView<Viajes> tableViajes;
    @FXML
    private TableColumn<Viajes, String> colDestino;
    @FXML
    private TableColumn<Viajes, LocalDate> colFechaSalida;
    @FXML
    private TableColumn<Viajes, LocalDate> colFechaRegreso;
    @FXML
    private TableColumn<Viajes, Double> colPrecio;

    private int idCliente; // ID del cliente que realiza la reserva

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @FXML
    public void initialize() {
        colDestino.setCellValueFactory(new PropertyValueFactory<>("Destino"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("Fecha_salida"));
        colFechaRegreso.setCellValueFactory(new PropertyValueFactory<>("Fecha_regreso"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));

        cargarViajes();
    }

    private void cargarViajes() {
        List<Viajes> viajes = ViajeDAO.findAll();
        tableViajes.getItems().setAll(viajes);
    }

    @FXML
    private void handleReservar() {
        Viajes viajeSeleccionado = tableViajes.getSelectionModel().getSelectedItem();
        if (viajeSeleccionado == null) {
            mostrarAlerta("Error", "Por favor, selecciona un viaje para reservar.");
            return;
        }

        Reservas nuevaReserva = new Reservas();
        nuevaReserva.setID_Cliente(idCliente);
        nuevaReserva.setID_Viaje(viajeSeleccionado.getID_Viaje());
        nuevaReserva.setFecha_salida(viajeSeleccionado.getFecha_salida()); // Fecha de salida del viaje seleccionado
        nuevaReserva.setFecha_regreso(viajeSeleccionado.getFecha_regreso()); // Fecha de regreso del viaje seleccionado
        nuevaReserva.setEstado("Pendiente");

        Reservas reservaInsertada = ReservaDAO.insertReserva(nuevaReserva);
        if (reservaInsertada != null) {
            mostrarAlerta("Éxito", "La reserva se ha realizado correctamente.");
            cerrarVentana();
        } else {
            mostrarAlerta("Error", "No se pudo realizar la reserva.");
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tableViajes.getScene().getWindow();
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

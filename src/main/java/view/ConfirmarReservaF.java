package view;

import DAO.ReservaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Reservas;

import java.time.LocalDate;

public class ConfirmarReservaF {

    private int idCliente;
    private int idViaje;

    public void setDatosReserva(int idCliente, int idViaje) {
        this.idCliente = idCliente;
        this.idViaje = idViaje;
    }

   @FXML
    private void handleConfirmar() {
        Reservas nuevaReserva = new Reservas();
        nuevaReserva.setID_Cliente(idCliente);
        nuevaReserva.setID_Viaje(idViaje);
        nuevaReserva.setFecha_salida(LocalDate.now());
        nuevaReserva.setFecha_regreso(LocalDate.now().plusDays(7)); // Ejemplo
        nuevaReserva.setEstado("Pendiente");

        Reservas reservaInsertada = ReservaDAO.insertReserva(nuevaReserva);
        if (reservaInsertada != null) {
            mostrarAlerta("Éxito", "La reserva se ha realizado correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo realizar la reserva.");
        }

        cerrarVentana();
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) Stage.getWindows().get(0);
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

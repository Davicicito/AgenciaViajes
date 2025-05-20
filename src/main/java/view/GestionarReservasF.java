package view;

import DAO.ReservaDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Reservas;

import java.io.IOException;
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
    private TableColumn<Reservas, String> colDestino; // Nueva columna
    @FXML
    private TableColumn<Reservas, Double> colPrecio;  // Nueva columna
    @FXML
    private TableColumn<Reservas, Integer> colIDAgente; // Nueva columna para el ID del agente

    private int idAgente; // Atributo para almacenar el ID del agente

    @FXML
    public void initialize() {
        colIDReserva.setCellValueFactory(new PropertyValueFactory<>("ID_Reserva"));
        colIDCliente.setCellValueFactory(new PropertyValueFactory<>("ID_Cliente"));
        colIDAgente.setCellValueFactory(new PropertyValueFactory<>("ID_Agente"));
        colIDViaje.setCellValueFactory(new PropertyValueFactory<>("ID_Viaje"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("Fecha_salida"));
        colFechaRegreso.setCellValueFactory(new PropertyValueFactory<>("Fecha_regreso"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("Destino"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));

        cargarReservasConDetalles(); // Llama al método sin parámetros
    }

    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente; // Asigna el ID del agente al atributo
        cargarReservasConDetalles(); // Cargar reservas después de asignar el ID del agente
    }

    private void cargarReservasConDetalles() {
        List<Reservas> reservas = ReservaDAO.findReservasByAgente(idAgente); // Usa el atributo idAgente
        tableReservas.getItems().setAll(reservas);
    }
    public void abrirGestionarReservas(int idAgente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GestionarReservasF.fxml"));
            Parent root = loader.load();

            GestionarReservasF controller = loader.getController();
            controller.setIdAgente(idAgente); // Pasa el ID del agente al controlador

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleEditarReserva() {
        Reservas reservaSeleccionada = tableReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Por favor, selecciona una reserva para editar.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Reserva");
        dialog.setHeaderText("Modifica los detalles de la reserva");

        DatePicker datePickerSalida = new DatePicker(reservaSeleccionada.getFecha_salida());
        datePickerSalida.setPromptText("Selecciona una nueva fecha de salida");

        DatePicker datePickerRegreso = new DatePicker(reservaSeleccionada.getFecha_regreso());
        datePickerRegreso.setPromptText("Selecciona una nueva fecha de regreso");

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

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                reservaSeleccionada.setFecha_salida(datePickerSalida.getValue());
                reservaSeleccionada.setFecha_regreso(datePickerRegreso.getValue());
                reservaSeleccionada.setEstado(comboBoxEstado.getValue());

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

    @FXML
    private void handleEliminarReserva() {
        Reservas reservaSeleccionada = tableReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Por favor, selecciona una reserva para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar esta reserva?");
        if (confirmacion.showAndWait().orElse(null) != ButtonType.OK) {
            return;
        }

        boolean eliminado = ReservaDAO.deleteReserva(reservaSeleccionada.getID_Reserva());
        if (eliminado) {
            tableReservas.getItems().remove(reservaSeleccionada);
            mostrarAlerta("Éxito", "La reserva ha sido eliminada correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo eliminar la reserva.");
        }
    }
    @FXML
    private void handleVolver() {
        Stage stage = (Stage) tableReservas.getScene().getWindow();
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

package view;

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

    private int idCliente; // Variable para almacenar el ID del cliente

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

        cargarReservas();
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
        cargarReservas(); // Cargar reservas específicas del cliente
    }

  private void cargarReservas() {
      List<Reservas> reservas = ReservaDAO.findByClienteId(idCliente); // Filtrar por ID del cliente
      tableReservas.getItems().setAll(reservas);
  }
  @FXML
  private void handleCancelarReserva() {
      Reservas reservaSeleccionada = tableReservas.getSelectionModel().getSelectedItem();
      if (reservaSeleccionada != null) {
          boolean eliminado = ReservaDAO.deleteReserva(reservaSeleccionada.getID_Reserva());
          if (eliminado) {
              cargarReservas(); // Actualizar la tabla
              mostrarMensaje("Reserva cancelada con éxito.");
          } else {
              mostrarMensaje("Error al cancelar la reserva.");
          }
      } else {
          mostrarMensaje("Selecciona una reserva para cancelar.");
      }
  }

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

      // Crear campos de edición
      DatePicker datePickerSalida = new DatePicker(reservaSeleccionada.getFecha_salida());
      DatePicker datePickerRegreso = new DatePicker(reservaSeleccionada.getFecha_regreso());
      ComboBox<String> comboBoxEstado = new ComboBox<>();
      comboBoxEstado.getItems().addAll("Aceptada", "Pendiente", "Cancelada");
      comboBoxEstado.setValue(reservaSeleccionada.getEstado());

      // Diseñar el formulario
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

      // Manejar la respuesta del usuario
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

  private void mostrarMensaje(String mensaje) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText(mensaje);
      alert.showAndWait();
  }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void handleVolver() {
        Stage stage = (Stage) tableReservas.getScene().getWindow();
        stage.close();
    }

}
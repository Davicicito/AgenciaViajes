package view;

import DAO.ViajeDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Viajes;

import java.io.IOException;
import java.util.List;

public class ViajesF {

    @FXML
    private TableView<Viajes> tableViajes;
    @FXML
    private TableColumn<Viajes, Integer> colID;
    @FXML
    private TableColumn<Viajes, String> colDestino;
    @FXML
    private TableColumn<Viajes, String> colFechaSalida;
    @FXML
    private TableColumn<Viajes, String> colFechaRegreso;
    @FXML
    private TableColumn<Viajes, Double> colPrecio;
    @FXML
    private TableColumn<Viajes, Integer> colPlazas;

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID_Viaje"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("Destino"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("Fecha_salida"));
        colFechaRegreso.setCellValueFactory(new PropertyValueFactory<>("Fecha_regreso"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        colPlazas.setCellValueFactory(new PropertyValueFactory<>("Plazas"));

        cargarViajes();
    }

    private void cargarViajes() {
        List<Viajes> viajes = ViajeDAO.findAll();
        tableViajes.getItems().setAll(viajes);
    }

    @FXML
    private void handleAgregarViaje() {
        // Lógica para agregar un nuevo viaje
    }

   @FXML
   private void handleEditarViaje() {
       Viajes viajeSeleccionado = tableViajes.getSelectionModel().getSelectedItem();
       if (viajeSeleccionado == null) {
           mostrarAlerta("Error", "Por favor, selecciona un viaje para editar.");
           return;
       }

       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditarViaje.fxml"));
           Parent root = loader.load();

           EditarViajeF controller = loader.getController();
           controller.setDatosViaje(viajeSeleccionado); // Pasar el viaje seleccionado al formulario

           Stage stage = new Stage();
           stage.setTitle("Editar Viaje");
           stage.setScene(new Scene(root));
           stage.showAndWait();

           // Refrescar la tabla después de editar
           cargarViajes();
       } catch (IOException e) {
           e.printStackTrace();
       }



   }

    @FXML
    private void handleEliminarViaje() {
        Viajes viajeSeleccionado = tableViajes.getSelectionModel().getSelectedItem();
        if (viajeSeleccionado == null) {
            mostrarAlerta("Error", "Por favor, selecciona un viaje para eliminar.");
            return;
        }

        boolean eliminado = ViajeDAO.deleteViaje(viajeSeleccionado.getID_Viaje());
        if (eliminado) {
            tableViajes.getItems().remove(viajeSeleccionado);
            mostrarAlerta("Éxito", "El viaje ha sido eliminado correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el viaje.");
        }
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
        Stage stage = (Stage) tableViajes.getScene().getWindow();
        stage.close();
    }
}

package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Viajes;
import DAO.ViajeDAO;

import java.io.IOException;
import java.util.List;

public class GestionarViajesF {

    @FXML
    private TableView<Viajes> tableViajes;
    @FXML
    private TableColumn<Viajes, Integer> colIDViaje;
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
        colIDViaje.setCellValueFactory(new PropertyValueFactory<>("ID_Viaje"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("Destino"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("Fecha_salida"));
        colFechaRegreso.setCellValueFactory(new PropertyValueFactory<>("Fecha_regreso"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        colPlazas.setCellValueFactory(new PropertyValueFactory<>("Plazas"));

        cargarViajes();
    }

    // Consulta todos los viajes desde la base de datos y los muestra en la tabla
    private void cargarViajes() {
        List<Viajes> viajes = ViajeDAO.findAll();
        tableViajes.getItems().setAll(viajes);
    }

    // Abre una ventana para añadir un nuevo viaje
    @FXML
    private void handleAgregarViaje() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditarViaje.fxml"));
            Parent root = loader.load();

            EditarViajeF controller = loader.getController();
            controller.setModoEdicion(false); // Indica que es modo "agregar viaje"

            Stage stage = new Stage();
            stage.setTitle("Añadir Viaje");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarViajes();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana para añadir un viaje.");
        }
    }

    // Abre una ventana para editar el viaje seleccionado
    @FXML
    private void handleEditarViaje() {
        Viajes viajeSeleccionado = tableViajes.getSelectionModel().getSelectedItem();
        if (viajeSeleccionado == null) {
            mostrarAlerta("Advertencia", "Por favor, selecciona un viaje para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditarViaje.fxml"));
            Parent root = loader.load();

            EditarViajeF controller = loader.getController();
            controller.setModoEdicion(true); // Indica que es modo "editar viaje"
            controller.cargarDatos(viajeSeleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Viaje");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarViajes();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana para editar el viaje.");
        }
    }

    // Elimina el viaje seleccionado
    @FXML
    private void handleEliminarViaje() {
        Viajes viajeSeleccionado = tableViajes.getSelectionModel().getSelectedItem();
        if (viajeSeleccionado == null) {
            mostrarAlerta("Advertencia", "Por favor, selecciona un viaje para eliminar.");
            return;
        }

        // Muestra un cuadro de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar este viaje?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            ViajeDAO.deleteViaje(viajeSeleccionado.getID_Viaje()); // Elimina el viaje
            cargarViajes();
        }
    }

    // Muestra una alerta informativa con el título y mensaje indicados
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Cierra la ventana actual
    public void handleVolver(ActionEvent actionEvent) {
        Stage stage = (Stage) tableViajes.getScene().getWindow();
        stage.close();
    }

}
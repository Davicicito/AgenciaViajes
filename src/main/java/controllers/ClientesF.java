package controllers;

import DAO.ClienteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Cliente;

import java.io.IOException;
import java.util.List;

public class ClientesF {

    @FXML
    private TableView<Cliente> tableClientes;
    @FXML
    private TableColumn<Cliente, Integer> colID;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colEmail;
    @FXML
    private TableColumn<Cliente, String> colDNI;

    // Método que se ejecuta al inicializar el controlador. Configura las columnas y carga los datos.
    @FXML
    public void initialize() {
        // Asociar columnas con las propiedades del modelo Cliente
        colID.setCellValueFactory(new PropertyValueFactory<>("ID_Usuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));

        // Obtener lista de clientes y añadirlos a la tabla
        List<Cliente> clientes = ClienteDAO.findAll();
        tableClientes.getItems().addAll(clientes);
    }

    // Abre la ventana para agregar un nuevo cliente
    @FXML
    private void handleAgregarCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AgregarCliente.fxml"));
            Parent root = loader.load();

            AgregarClienteF controller = loader.getController();
            controller.setTableClientes(tableClientes); // Pasar referencia de la tabla al nuevo controlador

            Stage stage = new Stage();
            stage.setTitle("Agregar Cliente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cierra la ventana actual (volver atrás)
    public void handleVolver(ActionEvent actionEvent) {
        Stage stage = (Stage) tableClientes.getScene().getWindow();
        stage.close();
    }

    // Abre la ventana para editar los datos de un cliente seleccionado
    public void handleEditarCliente(ActionEvent actionEvent) {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "Por favor, selecciona un cliente para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AgregarCliente.fxml"));
            Parent root = loader.load();

            AgregarClienteF controller = loader.getController();
            controller.setTableClientes(tableClientes); // Pasar tabla
            controller.cargarDatosCliente(clienteSeleccionado); // Cargar datos en el formulario

            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Muestra una alerta con un mensaje dado
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Elimina el cliente seleccionado, previa confirmación
    public void handleEliminarCliente(ActionEvent actionEvent) {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "Por favor, selecciona un cliente para eliminar.");
            return;
        }

        // Confirmar antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar este cliente?");
        if (confirmacion.showAndWait().orElse(null) != ButtonType.OK) {
            return;
        }

        // Llamada al DAO para eliminar el cliente
        boolean eliminado = ClienteDAO.deleteClienteByDNI(clienteSeleccionado.getDNI());
        if (eliminado) {
            tableClientes.getItems().remove(clienteSeleccionado); // Quitar de la tabla
            mostrarAlerta("Éxito", "El cliente ha sido eliminado correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el cliente.");
        }
    }

    // Abre una nueva ventana con las reservas del cliente seleccionado
    @FXML
    private void handleVerReservas() {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "Por favor, selecciona un cliente para ver sus reservas.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ReservasClientes.fxml"));
            Parent root = loader.load();

            ReservasClienteF controller = loader.getController();
            controller.setDniCliente(clienteSeleccionado.getDNI()); // Pasar el DNI del cliente
            Stage stage = new Stage();
            stage.setTitle("Mis Reservas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


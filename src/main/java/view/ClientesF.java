package view;

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

    @FXML
    public void initialize() {
        // Configurar las columnas
        colID.setCellValueFactory(new PropertyValueFactory<>("ID_Usuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));

        // Cargar datos de clientes
        List<Cliente> clientes = ClienteDAO.findAll();
        tableClientes.getItems().addAll(clientes);
    }

    @FXML
    private void handleAgregarCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AgregarCliente.fxml"));
            Parent root = loader.load();

            AgregarClienteF controller = loader.getController();
            controller.setTableClientes(tableClientes); // Pasar la referencia de la tabla

            Stage stage = new Stage();
            stage.setTitle("Agregar Cliente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleVolver(ActionEvent actionEvent) {
        // Cierra la ventana actual
        Stage stage = (Stage) tableClientes.getScene().getWindow();
        stage.close();
    }

    public void handleEditarCliente(ActionEvent actionEvent) {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            // Mostrar alerta si no se selecciona un cliente
            mostrarAlerta("Error", "Por favor, selecciona un cliente para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AgregarCliente.fxml"));
            Parent root = loader.load();

            AgregarClienteF controller = loader.getController();
            controller.setTableClientes(tableClientes); // Pasar la referencia de la tabla
            controller.cargarDatosCliente(clienteSeleccionado); // Cargar datos del cliente seleccionado

            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void handleAgregarReserva() {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "Por favor, selecciona un cliente para agregar una reserva.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SeleccionarViaje.fxml"));
            Parent root = loader.load();

            SeleccionarViajeF controller = loader.getController();
            controller.setIdCliente(clienteSeleccionado.getID_Usuario()); // Pasar el ID del cliente

            Stage stage = new Stage();
            stage.setTitle("Seleccionar Viaje");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEliminarCliente(ActionEvent actionEvent) {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            // Mostrar alerta si no se selecciona un cliente
            mostrarAlerta("Error", "Por favor, selecciona un cliente para eliminar.");
            return;
        }

        // Confirmación antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar este cliente?");
        if (confirmacion.showAndWait().orElse(null) != ButtonType.OK) {
            return;
        }

        // Eliminar cliente de la base de datos
        boolean eliminado = ClienteDAO.deleteClienteByID(clienteSeleccionado);
        if (eliminado) {
            // Eliminar cliente de la tabla
            tableClientes.getItems().remove(clienteSeleccionado);
            mostrarAlerta("Éxito", "El cliente ha sido eliminado correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el cliente.");
        }
    }
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
            controller.setIdCliente(clienteSeleccionado.getID_Usuario()); // Pasar el ID del cliente

            Stage stage = new Stage();
            stage.setTitle("Mis Reservas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }


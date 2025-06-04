package controllers;

import DAO.AgenteDAO;
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
import model.Agente;

import java.io.IOException;
import java.util.List;

public class AgentesF {

    @FXML
    private TableView<Agente> tableAgentes;
    @FXML
    private TableColumn<Agente, Integer> colID;
    @FXML
    private TableColumn<Agente, String> colNombre;
    @FXML
    private TableColumn<Agente, String> colEmail;
    @FXML
    private TableColumn<Agente, String> colCodigoEmpleado;
    @FXML
    private TableColumn<Agente, String> colOficina;
    @FXML
    private TableColumn<Agente, Boolean> colActivo;

    // Inicializa la tabla con las columnas y carga los datos de todos los agentes desde la base de datos
    @FXML
    public void initialize() {
        // Configurar las columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<>("Codigo_Empleado"));
        colOficina.setCellValueFactory(new PropertyValueFactory<>("Oficina"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        // Cargar datos de agentes
        List<Agente> agentes = AgenteDAO.findAll();
        tableAgentes.getItems().addAll(agentes);
    }
    // Abre la ventana para agregar un nuevo agente
    @FXML
    private void handleAgregarAgente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AgregarAgenteF.fxml"));
            Parent root = loader.load();

            AgregarAgenteF controller = loader.getController();
            controller.setTableAgentes(tableAgentes); // Pasar la referencia de la tabla

            Stage stage = new Stage();
            stage.setTitle("Agregar Agente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Abre la ventana para editar el agente seleccionado en la tabla
    @FXML
    private void handleEditarAgente() {
        Agente agenteSeleccionado = tableAgentes.getSelectionModel().getSelectedItem();
        if (agenteSeleccionado == null) {
            mostrarAlerta("Error", "Por favor, selecciona un agente para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AgregarAgenteF.fxml"));
            Parent root = loader.load();

            AgregarAgenteF controller = loader.getController();
            controller.setTableAgentes(tableAgentes); // Pasar la referencia de la tabla
            controller.cargarDatosAgente(agenteSeleccionado); // Cargar datos del agente seleccionado

            Stage stage = new Stage();
            stage.setTitle("Editar Agente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de edición.");
        }
    }

    // Elimina el agente seleccionado si se confirma la acción
   @FXML
   private void handleEliminarAgente() {
       Agente agenteSeleccionado = tableAgentes.getSelectionModel().getSelectedItem();
       if (agenteSeleccionado == null) {
           mostrarAlerta("Error", "Por favor, selecciona un agente para eliminar.");
           return;
       }

       Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
       confirmacion.setTitle("Confirmar eliminación");
       confirmacion.setHeaderText(null);
       confirmacion.setContentText("¿Estás seguro de que deseas eliminar este agente?");
       if (confirmacion.showAndWait().orElse(null) != ButtonType.OK) {
           return;
       }

       boolean eliminado = AgenteDAO.deleteAgenteByID(agenteSeleccionado);
       if (eliminado) {
           tableAgentes.getItems().remove(agenteSeleccionado);
           mostrarAlerta("Éxito", "El agente ha sido eliminado correctamente.");
       } else {
           mostrarAlerta("Error", "No se pudo eliminar el agente. Verifica si está relacionado con otras tablas.");
       }
   }
    // Muestra una alerta con un mensaje personalizado
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    // Abre la ventana para gestionar las reservas del agente seleccionado
    @FXML
    private void handleGestionarReservas() {
        Agente agenteSeleccionado = tableAgentes.getSelectionModel().getSelectedItem();
        if (agenteSeleccionado == null) {
            mostrarAlerta("Error", "Por favor, selecciona un agente para gestionar sus reservas.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GestionarReservas.fxml"));
            Parent root = loader.load();

            GestionarReservasF controller = loader.getController();
            controller.setCodigoEmpleado(agenteSeleccionado.getCodigo_Empleado()); // Pasar el Código de Empleado al controlador
            Stage stage = new Stage();
            stage.setTitle("Gestionar Reservas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de reservas.");
        }
    }

    // Cierra la ventana actual y vuelve a la anterior
    @FXML
    private void handleVolver(ActionEvent actionEvent) {
        Stage stage = (Stage) tableAgentes.getScene().getWindow();
        stage.close();
    }
}
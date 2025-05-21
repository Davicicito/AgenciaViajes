package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal {

    // Abre una nueva ventana con la vista de Clientes
    @FXML
    private void handleClienteButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Clientes.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Clientes");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Abre una nueva ventana con la vista de Agentes
    @FXML
    private void handleAgenteButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Agentes.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("Lista de Agentes");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Abre una nueva ventana para gestionar viajes
    @FXML
    private void handleGestionarViajes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GestionarViajes.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestión de Viajes");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de viajes.");
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

}

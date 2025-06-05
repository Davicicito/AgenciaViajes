package controllers;

import DAO.ClienteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Cliente;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class AgregarClienteF {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDNI;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private CheckBox chkVIP;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private TableView<Cliente> tableClientes;

    public void setTableClientes(TableView<Cliente> tableClientes) {
        this.tableClientes = tableClientes;
    }

    @FXML
    private void handleGuardar() {
        try {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String dni = txtDNI.getText().trim();
            String contraseña = txtContraseña.getText().trim();
            boolean vip = chkVIP.isSelected();

            validarNombre(nombre);
            validarEmail(email);
            validarDNI(dni);
            validarContraseña(contraseña);

            Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado != null) {
                clienteSeleccionado.setNombre(nombre);
                clienteSeleccionado.setEmail(email);
                clienteSeleccionado.setDNI(dni);
                clienteSeleccionado.setContraseña(contraseña);
                clienteSeleccionado.setVIP(vip);

                boolean actualizado = ClienteDAO.updateCliente(clienteSeleccionado);
                if (actualizado) {
                    tableClientes.refresh();
                    mostrarAlerta("Éxito", "El cliente ha sido actualizado correctamente.");
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el cliente.");
                }
            } else {
                Cliente nuevoCliente = new Cliente();
                nuevoCliente.setNombre(nombre);
                nuevoCliente.setEmail(email);
                nuevoCliente.setDNI(dni);
                nuevoCliente.setContraseña(contraseña);
                nuevoCliente.setVIP(vip);
                nuevoCliente.setFechaRegistro(LocalDate.now());

                Cliente clienteInsertado = ClienteDAO.insertCliente(nuevoCliente);
                if (clienteInsertado != null) {
                    tableClientes.getItems().add(clienteInsertado);
                    tableClientes.refresh();
                    mostrarAlerta("Éxito", "El cliente ha sido agregado correctamente.");
                } else {
                    mostrarAlerta("Error", "No se pudo agregar el cliente.");
                }
            }
            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    private void validarEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (email == null || !Pattern.matches(regex, email)) {
            throw new IllegalArgumentException("El email no tiene un formato válido.");
        }
    }

    private void validarContraseña(String contraseña) {
        if (contraseña == null || contraseña.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if (contraseña.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
    }

    private void validarDNI(String dni) {
        if (dni == null || dni.length() != 9) {
            throw new IllegalArgumentException("El DNI debe tener 9 caracteres.");
        }
    }

    public void cargarDatosCliente(Cliente cliente) {
        if (cliente != null) {
            txtNombre.setText(cliente.getNombre());
            txtEmail.setText(cliente.getEmail());
            txtDNI.setText(cliente.getDNI());
            txtContraseña.setText(cliente.getContraseña());
            chkVIP.setSelected(cliente.isVIP());
        }
    }
}
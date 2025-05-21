package controllers;

import DAO.ClienteDAO;
import exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cliente;

import java.util.regex.Pattern;

public class AgregarClienteF {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDNI;
    @FXML
    private PasswordField txtContraseña; // Campo para la contraseña

    private TableView<Cliente> tableClientes; // Referencia a la tabla de clientes

    // Establece la tabla de clientes que será usada para insertar o actualizar datos
    public void setTableClientes(TableView<Cliente> tableClientes) {
        this.tableClientes = tableClientes;
    }

    // Maneja la acción de guardar: valida los datos, actualiza o inserta el cliente en la base de datos
    @FXML
    private void handleGuardar() {
        try {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String dni = txtDNI.getText().trim();
            String contraseña = txtContraseña.getText().trim();

            if (contraseña == null || contraseña.isEmpty()) {
                mostrarAlerta("Error", "La contraseña no puede estar vacía.");
                return;
            }

            validarNombre(nombre);
            validarEmail(email);
            validarDNI(dni);
            validarContraseña(contraseña);

            Cliente clienteExistente = tableClientes.getSelectionModel().getSelectedItem();
            if (clienteExistente != null) {
                // Si ya existe, se actualiza el cliente
                clienteExistente.setNombre(nombre);
                clienteExistente.setEmail(email);
                clienteExistente.setDNI(dni);
                clienteExistente.setContraseña(contraseña);

                ClienteDAO.updateCliente(clienteExistente);
                tableClientes.refresh(); // Refresca la tabla para mostrar los cambios
            } else {
                // Si no existe, se inserta un nuevo cliente
                Cliente nuevoCliente = new Cliente();
                nuevoCliente.setNombre(nombre);
                nuevoCliente.setEmail(email);
                nuevoCliente.setDNI(dni);
                nuevoCliente.setContraseña(contraseña);

                Cliente clienteInsertado = ClienteDAO.insertCliente(nuevoCliente);
                if (clienteInsertado != null) {
                    tableClientes.getItems().add(clienteInsertado);
                    tableClientes.refresh();
                }
            }

            cerrarVentana(); // Cierra la ventana después de guardar
        } catch (ValidationException e) {
            mostrarAlerta("Error de validación", e.getMessage());
        }
    }

    // Valida que la contraseña no esté vacía y tenga al menos 6 caracteres
    private void validarContraseña(String contraseña) throws ValidationException {
        if (contraseña.isEmpty()) {
            throw new ValidationException("La contraseña no puede estar vacía.");
        }
        if (contraseña.length() < 6) {
            throw new ValidationException("La contraseña debe tener al menos 6 caracteres.");
        }
    }

    // Valida que el nombre no esté vacío
    private void validarNombre(String nombre) throws ValidationException {
        if (nombre.isEmpty()) {
            throw new ValidationException("El nombre no puede estar vacío.");
        }
    }

    // Valida el formato del email con una expresión regular
    private void validarEmail(String email) throws ValidationException {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.matches(regex, email)) {
            throw new ValidationException("El email no tiene un formato válido.");
        }
    }

    // Valida que el DNI tenga exactamente 9 caracteres
    private void validarDNI(String dni) throws ValidationException {
        if (dni.length() != 9) {
            throw new ValidationException("El DNI debe tener 9 caracteres.");
        }
    }

    // Maneja la acción de cancelar: cierra la ventana sin guardar
    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    // Cierra la ventana actual
    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    // Muestra una alerta emergente con un mensaje
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Carga los datos de un cliente en los campos del formulario para ser editado
    public void cargarDatosCliente(Cliente cliente) {
        txtNombre.setText(cliente.getNombre());
        txtEmail.setText(cliente.getEmail());
        txtDNI.setText(cliente.getDNI());
        txtContraseña.setText(cliente.getContraseña());
    }
}

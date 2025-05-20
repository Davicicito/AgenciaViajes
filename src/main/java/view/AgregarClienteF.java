package view;
import DAO.ClienteDAO;
import DAO.UsuarioDAO;
import exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cliente;
import model.Usuario;

import java.util.regex.Pattern;

public class AgregarClienteF {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDNI;
    @FXML
    private PasswordField txtContraseña; // Nuevo campo para la contraseña


    private TableView<Cliente> tableClientes; // Referencia a la tabla de clientes

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
                // Actualizar cliente existente
                clienteExistente.setNombre(nombre);
                clienteExistente.setEmail(email);
                clienteExistente.setDNI(dni);
                clienteExistente.setContraseña(contraseña);

                ClienteDAO.updateCliente(clienteExistente); // Actualizar en la base de datos
                tableClientes.refresh(); // Refrescar la tabla
            } else {
                // Insertar nuevo cliente
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

            cerrarVentana();
        } catch (ValidationException e) {
            mostrarAlerta("Error de validación", e.getMessage());
        }
    }
    private void validarContraseña(String contraseña) throws ValidationException {
        if (contraseña.isEmpty()) {
            throw new ValidationException("La contraseña no puede estar vacía.");
        }
        if (contraseña.length() < 6) {
            throw new ValidationException("La contraseña debe tener al menos 6 caracteres.");
        }
    }
    private void validarNombre(String nombre) throws ValidationException {
        if (nombre.isEmpty()) {
            throw new ValidationException("El nombre no puede estar vacío.");
        }
    }

    private void validarEmail(String email) throws ValidationException {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.matches(regex, email)) {
            throw new ValidationException("El email no tiene un formato válido.");
        }
    }

    private void validarDNI(String dni) throws ValidationException {
        if (dni.length() != 9) {
            throw new ValidationException("El DNI debe tener 9 caracteres.");
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cargarDatosCliente(Cliente cliente) {
        txtNombre.setText(cliente.getNombre());
        txtEmail.setText(cliente.getEmail());
        txtDNI.setText(cliente.getDNI());
        txtContraseña.setText(cliente.getContraseña());
    }
}
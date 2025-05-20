package view;

import DAO.AgenteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Agente;

import java.time.LocalDate;

public class AgregarAgenteF {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCodigoEmpleado;
    @FXML
    private TextField txtOficina;
    @FXML
    private CheckBox chkActivo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private TableView<Agente> tableAgentes;

    public void setTableAgentes(TableView<Agente> tableAgentes) {
        this.tableAgentes = tableAgentes;
    }

    @FXML
    private PasswordField txtContraseña; // Campo para la contraseña

   @FXML
    private void handleGuardar() {
        try {
            // Obtener los datos de los campos
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String codigoEmpleado = txtCodigoEmpleado.getText().trim();
            String oficina = txtOficina.getText().trim();
            String contraseña = txtContraseña.getText().trim();
            boolean activo = chkActivo.isSelected();

            // Validar los campos
            validarNombre(nombre);
            validarEmail(email);
            validarContraseña(contraseña);

            // Obtener el agente seleccionado
            Agente agenteSeleccionado = tableAgentes.getSelectionModel().getSelectedItem();
            if (agenteSeleccionado != null) {
                // Actualizar agente existente
                agenteSeleccionado.setNombre(nombre);
                agenteSeleccionado.setEmail(email);
                agenteSeleccionado.setCodigo_Empleado(codigoEmpleado);
                agenteSeleccionado.setOficina(oficina);
                agenteSeleccionado.setContraseña(contraseña);
                agenteSeleccionado.setActivo(activo);

                boolean actualizado = AgenteDAO.updateAgente(agenteSeleccionado);
                if (actualizado) {
                    tableAgentes.refresh();
                    mostrarAlerta("Éxito", "El agente ha sido actualizado correctamente.");
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el agente.");
                }
            } else {
                // Insertar nuevo agente
                Agente nuevoAgente = new Agente();
                nuevoAgente.setNombre(nombre);
                nuevoAgente.setEmail(email);
                nuevoAgente.setCodigo_Empleado(codigoEmpleado);
                nuevoAgente.setOficina(oficina);
                nuevoAgente.setContraseña(contraseña);
                nuevoAgente.setActivo(activo);

                Agente agenteInsertado = AgenteDAO.insertAgente(nuevoAgente);
                if (agenteInsertado != null) {
                    tableAgentes.getItems().add(agenteInsertado);
                    tableAgentes.refresh();
                    mostrarAlerta("Éxito", "El agente ha sido agregado correctamente.");
                } else {
                    mostrarAlerta("Error", "No se pudo agregar el agente.");
                }
            }

            // Cerrar la ventana
            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }
    private void validarNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    private void validarEmail(String email) throws IllegalArgumentException {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (email == null || !email.matches(regex)) {
            throw new IllegalArgumentException("El email no tiene un formato válido.");
        }
    }

    private void validarContraseña(String contraseña) throws IllegalArgumentException {
        if (contraseña == null || contraseña.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if (contraseña.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
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

    public void cargarDatosAgente(Agente agenteSeleccionado) {
        if (agenteSeleccionado != null) {
            txtNombre.setText(agenteSeleccionado.getNombre());
            txtEmail.setText(agenteSeleccionado.getEmail());
            txtCodigoEmpleado.setText(agenteSeleccionado.getCodigo_Empleado());
            txtOficina.setText(agenteSeleccionado.getOficina());
            chkActivo.setSelected(agenteSeleccionado.isActivo());
        }
    }
}

package model;

import java.time.LocalDate;

public class Cliente extends Usuario{
    private String DNI;

    // Constructor con parámetros que llama al constructor de la superclase
    public Cliente(int ID_Usuario, String nombre, String email, String contraseña, LocalDate fechaRegistro, String DNI) {
        super(ID_Usuario, nombre, email, contraseña, fechaRegistro);
        this.DNI = DNI;
    }

    // Constructor vacío que llama al constructor vacío de la superclase
    public Cliente() {
        super();
    }

    // Getter para el DNI
    public String getDNI() {
        return DNI;
    }

    // Setter para el DNI
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    // Método que genera un resumen descriptivo del cliente
    public String generarResumen() {
        return "Cliente: " + getNombre() + " (" + getEmail() + "), DNI: " + getDNI() + ", Pago: " + getID_Usuario() + ", Fecha: " + getFechaRegistro();
    }
}

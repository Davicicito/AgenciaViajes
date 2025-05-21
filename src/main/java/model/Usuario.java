package model;

import java.time.LocalDate;
import java.util.regex.Pattern;

public abstract class Usuario {
    protected int ID_Usuario;
    protected String Nombre;
    protected String Email;
    protected String Contraseña;
    protected LocalDate fechaRegistro;

    // Constructor con parámetros para inicializar un usuario
    public Usuario(int ID_Usuario, String nombre, String email, String contraseña, LocalDate fechaRegistro) {
        this.ID_Usuario = ID_Usuario;
        this.Nombre = nombre;
        this.Email = email;
        this.Contraseña = contraseña;
        this.fechaRegistro = fechaRegistro;
    }

    // Constructor vacío
    public Usuario() {
        // Constructor vacío
    }

    // Getter para el ID del usuario
    public int getID_Usuario() {
        return ID_Usuario;
    }

    // Setter para el ID del usuario
    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    // Getter para el nombre
    public String getNombre() {
        return Nombre;
    }

    // Setter para el nombre
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    // Getter para el email
    public String getEmail() {
        return Email;
    }

    // Setter para el email
    public void setEmail(String email) {
        Email = email;
    }

    // Getter para la contraseña
    public String getContraseña() {
        return Contraseña;
    }

    // Setter para la contraseña
    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    // Getter para la fecha de registro
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    // Setter para la fecha de registro
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // Método abstracto que debe ser implementado por las subclases para generar un resumen del usuario
    public abstract String generarResumen();
}


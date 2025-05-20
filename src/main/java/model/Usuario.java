package model;

import java.time.LocalDate;
import java.util.regex.Pattern;

public abstract class Usuario {
    protected int ID_Usuario;
    protected String Nombre;
    protected String Email;
    protected String Contraseña;
    protected LocalDate fechaRegistro;

    public Usuario(int ID_Usuario, String nombre, String email, String contraseña, LocalDate fechaRegistro) {
        this.ID_Usuario = ID_Usuario;
        this.Nombre = nombre;
        this.Email = email;
        this.Contraseña = contraseña;
        this.fechaRegistro = fechaRegistro;
    }
    public Usuario() {
        // Constructor vacío
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public abstract String generarResumen();
}

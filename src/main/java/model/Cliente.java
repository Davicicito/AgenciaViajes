package model;

import java.time.LocalDate;

public class Cliente extends Usuario{
    private String DNI;


    public Cliente(int ID_Usuario, String nombre, String email, String contraseña, LocalDate fechaRegistro, String DNI) {
        super(ID_Usuario, nombre, email, contraseña, fechaRegistro);
        this.DNI = DNI;
    }
    public Cliente() {
     super();
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }


    public String generarResumen() {
        return "Cliente: " + getNombre() + " (" + getEmail() + "), DNI: " + getDNI() + ", Pago: " + getID_Usuario() + ", Fecha: " + getFechaRegistro();
    }
}

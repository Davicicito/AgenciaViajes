package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{
    private String DNI;
    private boolean VIP;
    private final List<Reservas> reservas = new ArrayList<>();

    // Constructor con parámetros que llama al constructor de la superclase
    public Cliente(int ID_Usuario, String nombre, String email, String contraseña, LocalDate fechaRegistro, String DNI) {
        super(ID_Usuario, nombre, email, contraseña, fechaRegistro);
        this.DNI = DNI;
    }

    // Constructor vacío que llama al constructor vacío de la superclase
    public Cliente() {
        super();
    }
    public List<Reservas> getReservas() {
        return reservas;
    }

    public void addReserva(Reservas reserva) {
        reservas.add(reserva);
    }

    public void removeReserva(Reservas reserva) {
        reservas.remove(reserva);
    }

    // Getter para el DNI
    public String getDNI() {
        return DNI;
    }

    // Setter para el DNI
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    // Getter para el estado VIP
    public boolean isVIP() {
        return VIP;
    }
    // Setter para el estado VIP
    public void setVIP(boolean VIP) {
        this.VIP = VIP;
    }
    // Método para verificar si el cliente es VIP
    public boolean esVIP() {
        return VIP;
    }
    // Método para establecer el estado VIP del cliente
    public void establecerVIP(boolean estado) {
        this.VIP = estado;
    }
    // Método que devuelve una representación en cadena del cliente
    @Override
    public String toString() {
        return "Cliente{" +
                "ID_Usuario=" + getID_Usuario() +
                ", Nombre='" + getNombre() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", DNI='" + DNI + '\'' +
                ", VIP=" + VIP +
                ", FechaRegistro=" + getFechaRegistro() +
                '}';
    }

    // Método que genera un resumen descriptivo del cliente
    public String generarResumen() {
        return "Cliente: " + getNombre() + " (" + getEmail() + "), DNI: " + getDNI() + ", Pago: " + getID_Usuario() + ", Fecha: " + getFechaRegistro();
    }
}

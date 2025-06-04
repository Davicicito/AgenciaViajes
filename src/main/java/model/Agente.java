package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agente extends Usuario {
    private String Codigo_Empleado;
    private String Oficina;
    private boolean activo;
    private final List<Reservas> reservas = new ArrayList<>();

    // Constructor con todos los parámetros, incluyendo los heredados
    public Agente(int ID_Usuario, String nombre, String email, String contraseña, LocalDate fechaRegistro, String Codigo_Empleado, String Oficina, boolean activo) {
        super(ID_Usuario, nombre, email, contraseña, fechaRegistro);
        this.Codigo_Empleado = Codigo_Empleado;
        this.Oficina = Oficina;
        this.activo = activo;
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

    // Constructor vacío
    public Agente() {
        super();
    }

    // Getter y setter para Código de Empleado
    public String getCodigo_Empleado() {
        return Codigo_Empleado;
    }

    public void setCodigo_Empleado(String codigo_Empleado) {
        Codigo_Empleado = codigo_Empleado;
    }

    // Getter y setter para Oficina
    public String getOficina() {
        return Oficina;
    }

    public void setOficina(String oficina) {
        Oficina = oficina;
    }

    // Getter y setter para estado activo
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Método sobrescrito que genera un resumen descriptivo del agente
    @Override
    public String generarResumen() {
        String estado;
        if (activo) {
            estado = "Sí";
        } else {
            estado = "No";
        }
        return "Agente: " + getNombre() + " (" + getEmail() + "), Código: " + getCodigo_Empleado() + ", Oficina: " + getOficina() + ", Activo: " + estado;
    }
}

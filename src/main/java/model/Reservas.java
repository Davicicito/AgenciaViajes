package model;

import DAO.AgenteDAO;
import DAO.ViajeDAO;

import java.time.LocalDate;

public class Reservas {
    private int ID_Reserva;
    private int ID_Cliente;
    private int ID_Viaje;
    private int ID_Agente;
    private LocalDate Fecha_salida;
    private LocalDate Fecha_regreso;
    private String Estado;

    public Reservas(int ID_Reserva, int ID_Cliente, int ID_Viaje, int ID_Agente, LocalDate Fecha, String Estado) {
        this.ID_Reserva = ID_Reserva;
        this.ID_Cliente = ID_Cliente;
        this.ID_Viaje = ID_Viaje;
        this.ID_Agente = ID_Agente;
        this.Estado = Estado;
    }

    public Reservas() {
        // Constructor vacío
    }

    // Getters y Setters
    public int getID_Reserva() {
        return ID_Reserva;
    }

    public void setID_Reserva(int ID_Reserva) {
        this.ID_Reserva = ID_Reserva;
    }

    public int getID_Cliente() {
        return ID_Cliente;
    }

    public void setID_Cliente(int ID_Cliente) {
        this.ID_Cliente = ID_Cliente;
    }

    public int getID_Viaje() {
        return ID_Viaje;
    }

    public void setID_Viaje(int ID_Viaje) {
        this.ID_Viaje = ID_Viaje;
    }

    public int getID_Agente() {
        return ID_Agente;
    }

    public void setID_Agente(int ID_Agente) {
        this.ID_Agente = ID_Agente;
    }

    public LocalDate getFecha_salida() {
        return Fecha_salida;
    }

    public void setFecha_salida(LocalDate Fecha_salida) {
        this.Fecha_salida = Fecha_salida;
    }

    public LocalDate getFecha_regreso() {
        return Fecha_regreso;
    }

    public void setFecha_regreso(LocalDate Fecha_regreso) {
        this.Fecha_regreso = Fecha_regreso;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    // Obtener atributos de Viajes dinámicamente
    public String getDestino() {
        return ViajeDAO.findDestinoById(ID_Viaje);
    }

    public double getPrecio() {
        return ViajeDAO.findPrecioById(ID_Viaje);
    }

    public String getNombreAgente() {
        return AgenteDAO.findNombreById(ID_Agente);
    }
}
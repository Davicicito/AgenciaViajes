package model;

import DAO.AgenteDAO;
import DAO.ViajeDAO;

import java.time.LocalDate;

public class Reservas {
    private int ID_Reserva;
    private Agente agente;
    private Cliente cliente;
    private LocalDate Fecha_salida;
    private LocalDate Fecha_regreso;
    private String Estado;
    private Viajes viajes;


    public Reservas(int ID_Reserva, Agente agente,Cliente cliente, LocalDate fecha_salida, LocalDate fecha_regreso, String estado, Viajes viajes) {
        this.ID_Reserva = ID_Reserva;
        this.agente = agente;
        this.cliente = cliente;
        this.Fecha_salida = fecha_salida;
        this.Fecha_regreso = fecha_regreso;
        this.Estado = estado;
        this.viajes = viajes;
    }

    // Constructor vacío
    public Reservas() {
        // Constructor vacío
    }

    // Getters y Setters para cada atributo

    public int getID_Reserva() {
        return ID_Reserva;
    }

    public void setID_Reserva(int ID_Reserva) {
        this.ID_Reserva = ID_Reserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

// Getter para el objeto Viajes
public Viajes getViajes() {
    return viajes;
}

// Setter para el objeto Viajes
public void setViajes(Viajes viajes) {
    this.viajes = viajes;
}
    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
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

    public String getDNI() {
        return cliente.getDNI();
    }

    public String getCodigo_Empleado() {
        return agente.getCodigo_Empleado();
    }

    public int getID_Viaje() {
        return viajes.getID_Viaje();
    }

    // Obtener el destino del viaje asociado a esta reserva usando el DAO de Viaje
    public String getDestino() {
        return ViajeDAO.findDestinoById(viajes.getID_Viaje());
    }

    // Obtener el precio del viaje asociado a esta reserva usando el DAO de Viaje
    public double getPrecio() {
        return ViajeDAO.findPrecioById(viajes.getID_Viaje());
    }

    // Obtener el nombre del agente asociado a esta reserva usando el DAO de Agente
    public String getNombreAgente() {
        return AgenteDAO.findNombreByCodigo(agente.getCodigo_Empleado());
    }
}

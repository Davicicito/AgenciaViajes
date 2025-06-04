package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Viajes {
    private int ID_Viaje;
    private String Destino;
    private LocalDate Fecha_salida;
    private LocalDate Fecha_regreso;
    private double Precio;
    private int Plazas;
    private final List<Reservas> reservas = new ArrayList<>();

    // Constructor con parámetros para inicializar un viaje
    public Viajes(int ID_Viaje, String Destino, LocalDate Fecha_salida, LocalDate Fecha_regreso, double Precio, int Plazas) {
        this.ID_Viaje = ID_Viaje;
        this.Destino = Destino;
        this.Fecha_salida = Fecha_salida;
        this.Fecha_regreso = Fecha_regreso;
        this.Precio = Precio;
        this.Plazas = Plazas;
    }

    // Getter para la lista de reservas
    public List<Reservas> getReservas() {
        return reservas;
    }

    // Método para añadir una reserva a la lista
    public void addReserva(Reservas reserva) {
        reservas.add(reserva);
    }

    // Método para eliminar una reserva de la lista
    public void removeReserva(Reservas reserva) {
        reservas.remove(reserva);
    }
    // Constructor vacío
    public Viajes() {
        // Constructor vacío
    }

    // Getter para el ID del viaje
    public int getID_Viaje() {
        return ID_Viaje;
    }

    // Setter para el ID del viaje
    public void setID_Viaje(int ID_Viaje) {
        this.ID_Viaje = ID_Viaje;
    }

    // Getter para el destino del viaje
    public String getDestino() {
        return Destino;
    }

    // Setter para el destino del viaje
    public void setDestino(String destino) {
        Destino = destino;
    }

    // Getter para la fecha de salida
    public LocalDate getFecha_salida() {
        return Fecha_salida;
    }

    // Setter para la fecha de salida
    public void setFecha_salida(LocalDate fecha_salida) {
        Fecha_salida = fecha_salida;
    }

    // Getter para la fecha de regreso
    public LocalDate getFecha_regreso() {
        return Fecha_regreso;
    }

    // Setter para la fecha de regreso
    public void setFecha_regreso(LocalDate fecha_regreso) {
        Fecha_regreso = fecha_regreso;
    }

    // Getter para el precio
    public double getPrecio() {
        return Precio;
    }

    // Setter para el precio
    public void setPrecio(double precio) {
        Precio = precio;
    }

    // Getter para las plazas disponibles
    public int getPlazas() {
        return Plazas;
    }

    // Setter para las plazas disponibles
    public void setPlazas(int plazas) {
        Plazas = plazas;
    }

    // Método vacío para establecer estado, parece no estar implementado
    public void setEstado(String estado) {
        // Método vacío
    }
}


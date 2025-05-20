package model;

import java.time.LocalDate;

public class Viajes {
    private int ID_Viaje;
    private String Destino;
    private LocalDate Fecha_salida;
    private LocalDate Fecha_regreso;
    private double Precio;
    private int Plazas;

    public Viajes(int ID_Viaje, String Destino, LocalDate Fecha_salida, LocalDate Fecha_regreso, double Precio, int Plazas) {
        this.ID_Viaje = ID_Viaje;
        this.Destino = Destino;
        this.Fecha_salida = Fecha_salida;
        this.Fecha_regreso = Fecha_regreso;
        this.Precio = Precio;
        this.Plazas = Plazas;
    }
    public Viajes() {
        // Constructor vacío
    }

    public int getID_Viaje() {
        return ID_Viaje;
    }

    public void setID_Viaje(int ID_Viaje) {
        this.ID_Viaje = ID_Viaje;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public LocalDate getFecha_salida() {
        return Fecha_salida;
    }

    public void setFecha_salida(LocalDate fecha_salida) {
        Fecha_salida = fecha_salida;
    }

    public LocalDate getFecha_regreso() {
        return Fecha_regreso;
    }

    public void setFecha_regreso(LocalDate fecha_regreso) {
        Fecha_regreso = fecha_regreso;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public int getPlazas() {
        return Plazas;
    }

    public void setPlazas(int plazas) {
        Plazas = plazas;
    }

    public void setEstado(String estado) {
    }
}

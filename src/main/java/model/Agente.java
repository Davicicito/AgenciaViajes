package model;

import java.time.LocalDate;

public class Agente extends Usuario {
    private String Codigo_Empleado;
    private String Oficina;
    private boolean activo;

    public Agente(int ID_Usuario, String nombre, String email, String contraseña, LocalDate fechaRegistro, String Codigo_Empleado, String Oficina, boolean activo) {
        super(ID_Usuario, nombre, email, contraseña, fechaRegistro);
        this.Codigo_Empleado = Codigo_Empleado;
        this.Oficina = Oficina;
        this.activo = activo;
    }

    public Agente() {
        super();
    }

    public String getCodigo_Empleado() {
        return Codigo_Empleado;
    }

    public void setCodigo_Empleado(String codigo_Empleado) {
        Codigo_Empleado = codigo_Empleado;
    }

    public String getOficina() {
        return Oficina;
    }

    public void setOficina(String oficina) {
        Oficina = oficina;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

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

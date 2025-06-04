package DAO;

import baseDatos.ConnectionBD;
import model.Agente;
import model.Cliente;
import model.Reservas;
import model.Viajes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    // Consultas SQL para actualizar, borrar y buscar reservas
    private static final String SQL_ALL = "SELECT * FROM Reservas";
    private static final String SQL_UPDATE = "UPDATE Reservas SET DNI = ?, ID_Viaje = ?, Codigo_Empleado = ?, Fecha_salida = ?, Fecha_regreso = ?, Estado = ? WHERE ID_Reserva = ?";
    private static final String SQL_DELETE = "DELETE FROM Reservas WHERE ID_Reserva = ?";
    private static final String SQL_FIND_BY_CLIENT_DNI = "SELECT * FROM Reservas WHERE DNI = ?";
    private static final String SQL_FIND_RESERVAS_BY_AGENTE = "SELECT * FROM Reservas WHERE Codigo_Empleado = ?";

    public static List<Reservas> findAll() {
        List<Reservas> reservas = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {

            while (rs.next()) {
                Reservas reserva = new Reservas();
                reserva.setID_Reserva(rs.getInt("ID_Reserva"));

                Viajes viaje = new Viajes();
                viaje.setID_Viaje(rs.getInt("ID_Viaje"));
                reserva.setViajes(viaje);

                reserva.setFecha_salida(rs.getDate("Fecha_salida").toLocalDate());
                reserva.setFecha_regreso(rs.getDate("Fecha_regreso").toLocalDate());
                reserva.setEstado(rs.getString("Estado"));
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    // Método para actualizar una reserva en la base de datos
    public static boolean updateReserva(Reservas reserva) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

            pst.setString(1, reserva.getCliente().getDNI());
            pst.setInt(2, reserva.getViajes().getID_Viaje());
            pst.setString(3, reserva.getAgente().getCodigo_Empleado());
            pst.setDate(4, Date.valueOf(reserva.getFecha_salida()));
            pst.setDate(5, Date.valueOf(reserva.getFecha_regreso()));
            pst.setString(6, reserva.getEstado());
            pst.setInt(7, reserva.getID_Reserva());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Método para borrar una reserva por ID
    public static boolean deleteReserva(int id) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar todas las reservas de un cliente por su ID
    public static List<Reservas> findByClienteDNI(String dniCliente) {
        List<Reservas> reservas = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_CLIENT_DNI)) {

            pst.setString(1, dniCliente);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Reservas reserva = new Reservas();
                    reserva.setID_Reserva(rs.getInt("ID_Reserva"));

                    Viajes viaje = new Viajes();
                    viaje.setID_Viaje(rs.getInt("ID_Viaje"));
                    reserva.setViajes(viaje);

                    reserva.setFecha_salida(rs.getDate("Fecha_salida").toLocalDate());
                    reserva.setFecha_regreso(rs.getDate("Fecha_regreso").toLocalDate());
                    reserva.setEstado(rs.getString("Estado"));
                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    // Método para buscar todas las reservas asignadas a un agente por su ID
    public static List<Reservas> findReservasByAgente(String codigoEmpleado) {
        List<Reservas> reservas = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_RESERVAS_BY_AGENTE)) {

            pst.setString(1, codigoEmpleado);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Reservas reserva = new Reservas();
                    reserva.setID_Reserva(rs.getInt("ID_Reserva"));

                    Viajes viaje = new Viajes();
                    viaje.setID_Viaje(rs.getInt("ID_Viaje"));
                    reserva.setViajes(viaje);

                    Agente agente = new Agente();
                    agente.setCodigo_Empleado(rs.getString("Codigo_Empleado"));
                    reserva.setAgente(agente);

                    // Si quieres también el cliente:
                    Cliente cliente = new Cliente();
                    cliente.setDNI(rs.getString("DNI"));
                    reserva.setCliente(cliente);

                    reserva.setFecha_salida(rs.getDate("Fecha_salida").toLocalDate());
                    reserva.setFecha_regreso(rs.getDate("Fecha_regreso").toLocalDate());
                    reserva.setEstado(rs.getString("Estado"));
                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
}

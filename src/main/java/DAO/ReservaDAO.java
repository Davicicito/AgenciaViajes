package DAO;

import baseDatos.ConnectionBD;
import model.Reservas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    // Consultas SQL para actualizar, borrar y buscar reservas
    private static final String SQL_UPDATE = "UPDATE Reservas SET ID_Cliente = ?, ID_Viaje = ?, ID_Agente = ?, Fecha_salida = ?, Fecha_regreso = ?, Estado = ? WHERE ID_Reserva = ?";
    private static final String SQL_DELETE = "DELETE FROM Reservas WHERE ID_Reserva = ?";
    private static final String SQL_FIND_BY_CLIENT_ID = "SELECT * FROM Reservas WHERE ID_Cliente = ?";
    private static final String SQL_FIND_RESERVAS_BY_AGENTE = "SELECT * FROM Reservas WHERE ID_Agente = ?";

    // Método para actualizar una reserva en la base de datos
    public static boolean updateReserva(Reservas reserva) {
        boolean actualizado = false;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

            // Rellenar parámetros del PreparedStatement con los valores del objeto reserva
            pst.setInt(1, reserva.getID_Cliente());
            pst.setInt(2, reserva.getID_Viaje());
            pst.setInt(3, reserva.getID_Agente());
            pst.setDate(4, Date.valueOf(reserva.getFecha_salida()));
            pst.setDate(5, Date.valueOf(reserva.getFecha_regreso()));
            pst.setString(6, reserva.getEstado());
            pst.setInt(7, reserva.getID_Reserva());

            // Ejecutar la actualización y comprobar si afectó filas
            int filasAfectadas = pst.executeUpdate();
            actualizado = filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actualizado;
    }

    // Método para borrar una reserva por ID
    public static boolean deleteReserva(int id) {
        boolean eliminado = false;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {

            pst.setInt(1, id);
            int filasAfectadas = pst.executeUpdate();
            eliminado = filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eliminado;
    }

    // Método para buscar todas las reservas de un cliente por su ID
    public static List<Reservas> findByClienteId(int idCliente) {
        List<Reservas> reservas = new ArrayList<>();
        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_CLIENT_ID)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            // Crear objetos Reservas a partir del ResultSet y añadirlos a la lista
            while (rs.next()) {
                Reservas reserva = new Reservas();
                reserva.setID_Reserva(rs.getInt("ID_Reserva"));
                reserva.setID_Cliente(rs.getInt("ID_Cliente"));
                reserva.setID_Viaje(rs.getInt("ID_Viaje"));
                reserva.setID_Agente(rs.getInt("ID_Agente"));
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

    // Método para buscar todas las reservas asignadas a un agente por su ID
    public static List<Reservas> findReservasByAgente(int idAgente) {
        List<Reservas> reservas = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_RESERVAS_BY_AGENTE)) {

            pst.setInt(1, idAgente); // Filtrar por ID_Agente
            try (ResultSet rs = pst.executeQuery()) {
                // Crear objetos Reservas y añadirlos a la lista
                while (rs.next()) {
                    Reservas reserva = new Reservas();
                    reserva.setID_Reserva(rs.getInt("ID_Reserva"));
                    reserva.setID_Cliente(rs.getInt("ID_Cliente"));
                    reserva.setID_Viaje(rs.getInt("ID_Viaje"));
                    reserva.setFecha_salida(rs.getDate("Fecha_salida").toLocalDate());
                    reserva.setFecha_regreso(rs.getDate("Fecha_regreso").toLocalDate());
                    reserva.setEstado(rs.getString("Estado"));
                    reserva.setID_Agente(rs.getInt("ID_Agente"));
                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
}

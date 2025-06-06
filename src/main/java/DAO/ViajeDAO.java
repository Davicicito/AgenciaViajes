package DAO;

import baseDatos.ConnectionBD;
import model.Viajes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ViajeDAO {
    private static final String SQL_ALL = "SELECT * FROM Viajes";
    private static final String SQL_INSERT_VIAJE = "INSERT INTO Viajes (Destino, Fecha_salida, Fecha_regreso, Precio, Plazas) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM Viajes WHERE ID_Viaje = ?";
    private static final String SQL_UPDATE = "UPDATE Viajes SET Destino = ?, Fecha_salida = ?, Fecha_regreso = ?, Precio = ?, Plazas = ? WHERE ID_Viaje = ?";
    private static final String SQL_FIND_DESTINO_BY_ID = "SELECT Destino FROM Viajes WHERE ID_Viaje = ?";
    private static final String SQL_FIND_PRECIO_BY_ID = "SELECT Precio FROM Viajes WHERE ID_Viaje = ?";

    // Obtiene una lista con todos los viajes registrados en la base de datos.
    // Devuelve una lista de objetos Viajes.
    public static List<Viajes> findAll() {
        List<Viajes> viajes = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {

            while (rs.next()) {
                Viajes viaje = new Viajes();
                viaje.setID_Viaje(rs.getInt("ID_Viaje"));
                viaje.setDestino(rs.getString("Destino"));
                viaje.setFecha_salida(rs.getDate("Fecha_salida").toLocalDate());
                viaje.setFecha_regreso(rs.getDate("Fecha_regreso").toLocalDate());
                viaje.setPrecio(rs.getDouble("Precio"));
                viaje.setPlazas(rs.getInt("Plazas"));
                viajes.add(viaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viajes;
    }

    // Inserta un nuevo viaje en la base de datos.
// Recibe un objeto Viajes y devuelve el mismo con el ID asignado si la inserción fue exitosa.
// Devuelve null en caso de error.
    public static Viajes insertViaje(Viajes viaje) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT_VIAJE, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, viaje.getDestino());
            pst.setDate(2, Date.valueOf(viaje.getFecha_salida()));
            pst.setDate(3, Date.valueOf(viaje.getFecha_regreso()));
            pst.setDouble(4, viaje.getPrecio());
            pst.setInt(5, viaje.getPlazas());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    viaje.setID_Viaje(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            viaje = null;
        }
        return viaje;
    }
    // Elimina un viaje de la base de datos según su ID.
// Devuelve true si la eliminación fue correcta, false en caso contrario.
public static boolean deleteViaje(int idViaje) {
    boolean eliminado = false;

    try (Connection con = ConnectionBD.getConnection();
         PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {

        pst.setInt(1, idViaje); // Asigna el ID del viaje al parámetro
        int filasAfectadas = pst.executeUpdate();
        eliminado = filasAfectadas > 0; // Verifica si se eliminó alguna fila
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return eliminado;
}
    // Actualiza los datos de un viaje existente en la base de datos.
// Recibe un objeto Viajes con los datos actualizados.
// Devuelve true si la actualización fue correcta, false en caso contrario.
 public static boolean updateViaje(Viajes viaje) {
     boolean actualizado = false;

     try (Connection con = ConnectionBD.getConnection();
          PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

         pst.setString(1, viaje.getDestino());
         pst.setDate(2, Date.valueOf(viaje.getFecha_salida()));
         pst.setDate(3, Date.valueOf(viaje.getFecha_regreso()));
         pst.setDouble(4, viaje.getPrecio());
         pst.setInt(5, viaje.getPlazas());
         pst.setInt(6, viaje.getID_Viaje());
         int filasAfectadas = pst.executeUpdate();
         actualizado = filasAfectadas > 0;
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return actualizado;
 }
    // Obtiene el destino de un viaje dado su ID.
// Devuelve el destino como String o un mensaje por defecto si no se encuentra.
    public static String findDestinoById(int idViaje) {
        String destino = "Destino no disponible";
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_DESTINO_BY_ID)) {

            pst.setInt(1, idViaje);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    destino = rs.getString("Destino");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destino;
    }
    // Obtiene el precio de un viaje dado su ID.
// Devuelve el precio como double o 0.0 si no se encuentra.
    public static double findPrecioById(int idViaje) {
        double precio = 0.0;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_PRECIO_BY_ID)) {

            pst.setInt(1, idViaje);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    precio = rs.getDouble("Precio");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precio;
    }
}

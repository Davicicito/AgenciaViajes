package DAO;

import baseDatos.ConnectionBD;
import model.Agente;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteDAO {
    private static final String SQL_ALL = "SELECT * FROM Usuario u JOIN Agente a ON u.ID_Usuario = a.ID_Usuario";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM Usuario u JOIN Agente a ON u.ID_Usuario = a.ID_Usuario WHERE u.Email = ?";
    private static final String SQL_INSERT_AGENTE = "INSERT INTO Agente (ID_Usuario, Codigo_Empleado, Oficina, Activo) VALUES (?, ?, ?, ?)";
    private static final String SQL_CHECK_IF_CLIENTE = "SELECT 1 FROM Clientes WHERE ID_Usuario = ?";
    private static final String SQL_UPDATE_AGENTE = "UPDATE Agente SET Codigo_Empleado = ?, Oficina = ?, Activo = ? WHERE ID_Usuario = ?";
    private static final String SQL_FIND_NOMBRE_BY_ID = "SSELECT Nombre FROM Agentes WHERE ID_Agente = ?";

    // Obtener todos los agentes
    public static List<Agente> findAll() {
        List<Agente> agentes = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {

            while (rs.next()) {
                Agente agente = new Agente();
                agente.setID_Usuario(rs.getInt("ID_Usuario"));
                agente.setNombre(rs.getString("Nombre"));
                agente.setEmail(rs.getString("Email"));
                agente.setContraseña(rs.getString("Contraseña"));
                agente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                agente.setCodigo_Empleado(rs.getString("Codigo_Empleado"));
                agente.setOficina(rs.getString("Oficina"));
                agente.setActivo(rs.getBoolean("Activo"));
                agentes.add(agente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agentes;
    }

    // Buscar agente por email
    public static Agente findByEmail(String email) {
        Agente agente = null;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_EMAIL)) {

            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    agente = new Agente();
                    agente.setID_Usuario(rs.getInt("ID_Usuario"));
                    agente.setNombre(rs.getString("Nombre"));
                    agente.setEmail(rs.getString("Email"));
                    agente.setContraseña(rs.getString("Contraseña"));
                    agente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                    agente.setCodigo_Empleado(rs.getString("Codigo_Empleado"));
                    agente.setOficina(rs.getString("Oficina"));
                    agente.setActivo(rs.getBoolean("Activo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agente;
    }

    // Insertar nuevo agente
    public static Agente insertAgente(Agente agente) {
        if (agente == null || findByEmail(agente.getEmail()) != null) {
            System.out.println("El agente ya existe o los datos son nulos.");
            return null; // Evitar duplicados o datos nulos
        }

        Usuario usuarioInsertado = UsuarioDAO.insertUsuario(agente);
        if (usuarioInsertado != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_INSERT_AGENTE)) {

                pst.setInt(1, usuarioInsertado.getID_Usuario());
                pst.setString(2, agente.getCodigo_Empleado());
                pst.setString(3, agente.getOficina());
                pst.setBoolean(4, agente.isActivo());
                pst.executeUpdate();

                agente.setID_Usuario(usuarioInsertado.getID_Usuario());
                return agente;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    // Actualiza los datos del agente (solo en Usuario)
   public static boolean updateAgente(Agente agente) {
       if (agente == null) {
           System.out.println("El agente es nulo.");
           return false;
       }

       // Actualizar el usuario asociado
       boolean usuarioActualizado = UsuarioDAO.updateUsuario(agente);
       if (!usuarioActualizado) {
           System.out.println("No se pudo actualizar el usuario asociado.");
           return false;
       }

       // Actualizar los datos específicos del agente
       try (Connection con = ConnectionBD.getConnection();
            PreparedStatement pst = con.prepareStatement(SQL_UPDATE_AGENTE)) {

           pst.setString(1, agente.getCodigo_Empleado());
           pst.setString(2, agente.getOficina());
           pst.setBoolean(3, agente.isActivo());
           pst.setInt(4, agente.getID_Usuario());
           return pst.executeUpdate() > 0;

       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }

    public static boolean deleteAgenteByID(Agente agente) {
        if (agente == null) {
            return false;
        }

        try {
            // Verificar si el agente existe como cliente
            if (existeComoCliente(agente.getID_Usuario())) {
                System.out.println("El agente no se puede eliminar porque está registrado como cliente.");
                return false;
            }

            // Eliminar el usuario asociado al agente
            return UsuarioDAO.deleteUsuarioByID(agente);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String findNombreById(int idAgente) {
        String nombre = "";
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_NOMBRE_BY_ID)) {

            pst.setInt(1, idAgente);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("Nombre");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    // Verifica si el usuario ya existe en la tabla cliente
    private static boolean existeComoCliente(int idUsuario) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHECK_IF_CLIENTE)) {

            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // true si hay una fila
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


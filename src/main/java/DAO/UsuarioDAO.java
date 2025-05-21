package DAO;

import baseDatos.ConnectionBD;
import model.Cliente;
import model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static DAO.ClienteDAO.findByEmail;

public class UsuarioDAO {
    private static final String SQL_INSERT_USUARIO = "INSERT INTO Usuario (Nombre, Email, Contraseña, Fecha_Registro) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Usuario SET Nombre = ?, Email = ?, Contraseña = ?, Fecha_Registro = ? WHERE ID_Usuario = ?";
    private static final String SQL_DELETE = "DELETE FROM Usuario WHERE ID_Usuario = ?";



    // Inserta un nuevo usuario en la base de datos si no existe otro con el mismo email.
    // Recibe un objeto Usuario y devuelve el mismo objeto con el ID asignado si la inserción fue exitosa.
    // Devuelve null si el usuario es nulo o ya existe otro con ese email.
    public static Usuario insertUsuario(Usuario usuario) {
        if (usuario != null && findByEmail(usuario.getEmail()) == null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_INSERT_USUARIO, Statement.RETURN_GENERATED_KEYS)) {

                // Depuración: Verificar los valores antes de la inserción
                System.out.println("Insertando usuario:");
                System.out.println("Nombre: " + usuario.getNombre());
                System.out.println("Email: " + usuario.getEmail());
                System.out.println("Contraseña: " + usuario.getContraseña());

                if (usuario.getContraseña() == null || usuario.getContraseña().isEmpty()) {
                    throw new IllegalArgumentException("La contraseña no puede ser null o vacía.");
                }

                pst.setString(1, usuario.getNombre());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getContraseña());
                pst.setDate(4, Date.valueOf(LocalDate.now())); // Fecha actual
                pst.executeUpdate();

                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int idUsuario = rs.getInt(1);
                    usuario.setID_Usuario(idUsuario);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                usuario = null; // En caso de error, devolver null
            }
        } else {
            usuario = null; // Si ya existe un usuario con ese email, devolver null
        }
        return usuario;
    }
        // Actualiza un usuario existente en la base de datos.
        // Recibe un objeto Usuario con los datos actualizados.
        // Devuelve true si la actualización fue correcta, false en caso contrario.
    public static boolean updateUsuario(Usuario usuario) {
        boolean actualizado = false;
        if (usuario != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

                pst.setString(1, usuario.getNombre());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getContraseña());
                pst.setDate(4, Date.valueOf(usuario.getFechaRegistro()));
                pst.setInt(5, usuario.getID_Usuario());
                actualizado = pst.executeUpdate() > 0; // Verifica si se actualizó correctamente
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return actualizado;
    }
    // Elimina un usuario de la base de datos utilizando el ID del objeto Usuario proporcionado.
    // Devuelve true si la eliminación fue correcta, false en caso contrario.
   public static boolean deleteUsuarioByID(Usuario usuario) {
       boolean eliminado = false;
       if (usuario != null) {
           try (Connection con = ConnectionBD.getConnection();
                PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {

               pst.setInt(1, usuario.getID_Usuario());
               eliminado = pst.executeUpdate() > 0; // Devuelve true si se eliminó correctamente
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       return eliminado;
   }



}

package DAO;

import baseDatos.ConnectionBD;
import exceptions.CodigoEmpleadoRepetidoException;
import model.Agente;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteDAO {
    private static final String SQL_ALL = "SELECT * FROM Agente";
    private static final String SQL_INSERT_AGENTE = "INSERT INTO Agente (Codigo_Empleado, Nombre, Email, Contraseña, Fecha_Registro, Oficina, Activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_AGENTE = "UPDATE Agente SET Nombre = ?, Email = ?, Contraseña = ?, Fecha_Registro = ?, Oficina = ?, Activo = ? WHERE Codigo_Empleado = ?";
    private static final String SQL_DELETE_AGENTE = "DELETE FROM Agente WHERE Codigo_Empleado = ?";
    private static final String SQL_FIND_BY_CODIGO_EMPLEADO = "SELECT * FROM Agente WHERE Codigo_Empleado = ?";
    // Obtener todos los agentes
    public static List<Agente> findAll() {
        List<Agente> agentes = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {

            while (rs.next()) {
                Agente agente = new Agente();
                agente.setCodigo_Empleado(rs.getString("Codigo_Empleado"));
                agente.setNombre(rs.getString("Nombre"));
                agente.setEmail(rs.getString("Email"));
                agente.setContraseña(rs.getString("Contraseña"));
                agente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                agente.setOficina(rs.getString("Oficina"));
                agente.setActivo(rs.getBoolean("Activo"));
                agentes.add(agente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agentes;
    }


    public static Agente insertAgente(Agente agente) throws CodigoEmpleadoRepetidoException {
        if (findByCodigoEmpleado(agente.getCodigo_Empleado()) != null) {
            throw new CodigoEmpleadoRepetidoException("El código de empleado está repetido.");
        }
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT_AGENTE)) {

            pst.setString(1, agente.getCodigo_Empleado());
            pst.setString(2, agente.getNombre());
            pst.setString(3, agente.getEmail());
            pst.setString(4, agente.getContraseña());
            pst.setDate(5, Date.valueOf(agente.getFechaRegistro()));
            pst.setString(6, agente.getOficina());
            pst.setBoolean(7, agente.isActivo());
            pst.executeUpdate();
            return agente;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateAgente(Agente agente) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_AGENTE)) {

            pst.setString(1, agente.getNombre());
            pst.setString(2, agente.getEmail());
            pst.setString(3, agente.getContraseña());
            pst.setDate(4, Date.valueOf(agente.getFechaRegistro()));
            pst.setString(5, agente.getOficina());
            pst.setBoolean(6, agente.isActivo());
            pst.setString(7, agente.getCodigo_Empleado());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Agente findByCodigoEmpleado(String codigoEmpleado) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_CODIGO_EMPLEADO)) {

            pst.setString(1, codigoEmpleado);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Agente agente = new Agente();
                    agente.setCodigo_Empleado(rs.getString("Codigo_Empleado"));
                    agente.setNombre(rs.getString("Nombre"));
                    agente.setEmail(rs.getString("Email"));
                    agente.setContraseña(rs.getString("Contraseña"));
                    agente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                    agente.setOficina(rs.getString("Oficina"));
                    agente.setActivo(rs.getBoolean("Activo"));
                    return agente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteAgenteByID(Agente agente) {
        if (agente == null || agente.getCodigo_Empleado() == null) {
            return false;
        }

        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_AGENTE)) {

            pst.setString(1, agente.getCodigo_Empleado());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


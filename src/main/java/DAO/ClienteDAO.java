package DAO;

    import baseDatos.ConnectionBD;
    import model.Cliente;
    import model.Usuario;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class ClienteDAO {

        private static final String SQL_ALL = "SELECT * FROM Cliente";
        private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM Cliente WHERE Email = ?";
        private static final String SQL_INSERT_CLIENTE = "INSERT INTO Cliente (DNI, Nombre, Email, Contraseña, Fecha_Registro, VIP) VALUES (?, ?, ?, ?, ?, ?)";
        private static final String SQL_UPDATE_CLIENTE = "UPDATE Cliente SET Nombre = ?, Email = ?, Contraseña = ?, Fecha_Registro = ?, VIP = ? WHERE DNI = ?";
        private static final String SQL_DELETE_CLIENTE = "DELETE FROM Cliente WHERE DNI = ?";

        // Obtener todos los usuarios (cliente)
        public static List<Cliente> findAll() {
            List<Cliente> clientes = new ArrayList<>();
            try (Connection con = ConnectionBD.getConnection();
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(SQL_ALL)) {

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setDNI(rs.getString("DNI"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setEmail(rs.getString("Email"));
                    cliente.setContraseña(rs.getString("Contraseña"));
                    cliente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                    cliente.setVIP(rs.getBoolean("VIP"));
                    clientes.add(cliente);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return clientes;
        }

        public static Cliente findByEmail(String email) {
            Cliente cliente = null;
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_EMAIL)) {

                pst.setString(1, email);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        cliente = new Cliente();
                        cliente.setDNI(rs.getString("DNI"));
                        cliente.setNombre(rs.getString("Nombre"));
                        cliente.setEmail(rs.getString("Email"));
                        cliente.setContraseña(rs.getString("Contraseña"));
                        cliente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                        cliente.setVIP(rs.getBoolean("VIP"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return cliente;
        }

        public static Cliente insertCliente(Cliente cliente) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_INSERT_CLIENTE)) {

                pst.setString(1, cliente.getDNI());
                pst.setString(2, cliente.getNombre());
                pst.setString(3, cliente.getEmail());
                pst.setString(4, cliente.getContraseña());
                pst.setDate(5, Date.valueOf(cliente.getFechaRegistro()));
                pst.setBoolean(6, cliente.isVIP());
                pst.executeUpdate();
                return cliente;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static boolean updateCliente(Cliente cliente) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_UPDATE_CLIENTE)) {

                pst.setString(1, cliente.getNombre());
                pst.setString(2, cliente.getEmail());
                pst.setString(3, cliente.getContraseña());
                pst.setDate(4, Date.valueOf(cliente.getFechaRegistro()));
                pst.setBoolean(5, cliente.isVIP());
                pst.setString(6, cliente.getDNI());
                return pst.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public static boolean deleteClienteByDNI(String dni) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_DELETE_CLIENTE)) {

                pst.setString(1, dni);
                return pst.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
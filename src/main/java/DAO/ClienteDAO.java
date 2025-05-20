package DAO;

    import baseDatos.ConnectionBD;
    import model.Cliente;
    import model.Usuario;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class ClienteDAO {

        private static final String SQL_ALL = "SELECT * FROM Usuario u JOIN Clientes c ON u.ID_Usuario = c.ID_Usuario";
        private static final String SQL_FIND_BY_ID = "SELECT * FROM Usuario u JOIN Clientes c ON u.ID_Usuario = c.ID_Usuario WHERE u.ID_Usuario = ?";
        private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM Usuario u JOIN Clientes c ON u.ID_Usuario = c.ID_Usuario WHERE u.Email = ?";
        private static final String SQL_INSERT_CLIENTE = "INSERT INTO Clientes (ID_Usuario, DNI) VALUES (?, ?)";
        private static final String SQL_CHECK_IF_AGENTE = "SELECT 1 FROM Agente WHERE ID_Usuario = ?";
        private static final String SQL_UPDATE_CLIENTES = "UPDATE Clientes SET DNI = ? WHERE ID_Usuario = ?" ;

        // Obtener todos los usuarios (cliente)
        public static List<Cliente> findAll() {
            List<Cliente> clientes = new ArrayList<>();
            try (Connection con = ConnectionBD.getConnection();
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(SQL_ALL)) {

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setID_Usuario(rs.getInt("ID_Usuario"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setEmail(rs.getString("Email"));
                    cliente.setContraseña(rs.getString("Contraseña"));
                    cliente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                    cliente.setDNI(rs.getString("DNI"));
                    clientes.add(cliente);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return clientes;
        }
        // Busca un cliente por su email
        public static Cliente findByEmail(String email) {
            Cliente cliente = null;
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_EMAIL)) {

                pst.setString(1, email);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        cliente = new Cliente();
                        cliente.setID_Usuario(rs.getInt("ID_Usuario"));
                        cliente.setNombre(rs.getString("Nombre"));
                        cliente.setEmail(rs.getString("Email"));
                        cliente.setContraseña(rs.getString("Contraseña"));
                        cliente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                        cliente.setDNI(rs.getString("DNI"));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return cliente;
        }

        // Inserta un nuevo cliente (primero en Usuario, luego en Cliente)
        public static Cliente insertCliente(Cliente cliente) {
            if (cliente != null && findByEmail(cliente.getEmail()) == null) {
                // Insertar en la tabla Usuario usando UsuarioDAO
                Usuario usuarioInsertado = UsuarioDAO.insertUsuario(cliente); // La contraseña ya está en el cliente
                if (usuarioInsertado != null) {
                    try (Connection con = ConnectionBD.getConnection();
                         PreparedStatement pst = con.prepareStatement(SQL_INSERT_CLIENTE)) {

                        // Insertar en la tabla Clientes
                        pst.setInt(1, usuarioInsertado.getID_Usuario());
                        pst.setString(2, cliente.getDNI());
                        pst.executeUpdate();

                        // Asignar el ID generado al cliente
                        cliente.setID_Usuario(usuarioInsertado.getID_Usuario());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        cliente = null; // En caso de error, devolver null
                    }
                } else {
                    cliente = null; // Si no se pudo insertar en Usuario, devolver null
                }
            } else {
                cliente = null; // Si ya existe un cliente con ese email, devolver null
            }
            return cliente;
        }
        // Busca un cliente por su ID
        public static Cliente findById(int id) {
            Cliente cliente = null;
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {

                pst.setInt(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        cliente = new Cliente();
                        cliente.setID_Usuario(rs.getInt("ID_Usuario"));
                        cliente.setNombre(rs.getString("Nombre"));
                        cliente.setEmail(rs.getString("Email"));
                        cliente.setContraseña(rs.getString("Contraseña"));
                        cliente.setFechaRegistro(rs.getDate("Fecha_Registro").toLocalDate());
                        cliente.setDNI(rs.getString("DNI"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return cliente;
        }

        public static boolean updateCliente(Cliente cliente) {
            if (cliente == null) {
                return false;
            }

            // Actualizar en la tabla Usuario
            boolean usuarioActualizado = UsuarioDAO.updateUsuario(cliente);
            if (!usuarioActualizado) {
                return false;
            }

            // Actualizar en la tabla Clientes
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_UPDATE_CLIENTES)) {

                pst.setString(1, cliente.getDNI());
                pst.setInt(2, cliente.getID_Usuario());
                return pst.executeUpdate() > 0; // Devuelve true si se actualizó correctamente

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Elimina un cliente por ID (desde tabla Usuario, se asume ON DELETE CASCADE)
        public static boolean deleteClienteByID(Cliente cliente) {
            return cliente != null && UsuarioDAO.deleteUsuarioByID(cliente);
        }
        // Verifica si el usuario ya existe en la tabla agente
        public static boolean existeComoAgente(int idUsuario) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_CHECK_IF_AGENTE)) {

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


import DAO.ClienteDAO;
import model.Cliente;

import java.time.LocalDate;
import java.util.List;
public class MainTestDAO {
    public static void main(String[] args) {

        // 1. Crear un nuevo cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre("Juan Pérez");
        nuevoCliente.setEmail("juan@example.com");
        nuevoCliente.setContraseña("1234");
        nuevoCliente.setFechaRegistro(LocalDate.now());
        nuevoCliente.setDNI("12345678A");

        Cliente clienteInsertado = ClienteDAO.insertCliente(nuevoCliente);
        if (clienteInsertado != null) {
            System.out.println("Cliente insertado con ID: " + clienteInsertado.getID_Usuario());
        } else {
            System.out.println("No se pudo insertar el cliente (puede que ya exista).");
        }

        // 2. Buscar cliente por email
        Cliente clienteBuscado = ClienteDAO.findByEmail("juan@example.com");
        if (clienteBuscado != null) {
            System.out.println("Cliente encontrado: " + clienteBuscado.getNombre() + ", DNI: " + clienteBuscado.getDNI());
        } else {
            System.out.println("Cliente no encontrado.");
        }

        // 3. Actualizar cliente
        if (clienteBuscado != null) {
            clienteBuscado.setNombre("Juan Actualizado");
            boolean actualizado = ClienteDAO.updateCliente(clienteBuscado);
            System.out.println(actualizado ? "Cliente actualizado." : "No se pudo actualizar el cliente.");
        }

        // 4. Listar todos los clientes
        List<Cliente> todos = ClienteDAO.findAll();
        System.out.println("\n--- Lista de Clientes ---");
        for (Cliente c : todos) {
            System.out.println(c.getID_Usuario() + ": " + c.getNombre() + " - " + c.getEmail() + " - DNI: " + c.getDNI());
        }

        // 5. Eliminar cliente
        if (clienteBuscado != null) {
            boolean eliminado = ClienteDAO.deleteClienteByID(clienteBuscado);
            System.out.println(eliminado ? "Cliente eliminado." : "No se pudo eliminar el cliente.");
        }
    }
}

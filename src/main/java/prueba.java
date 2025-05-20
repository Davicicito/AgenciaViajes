import DAO.ClienteDAO;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class prueba {
    @Test
    public void testExisteComoAgente() {
        // ID de usuario que existe en la tabla Agente
        int idUsuarioExistente = 3; // Asegúrate de que este ID exista en la tabla Agente
        assertTrue("El método debería devolver true para un usuario existente en la tabla Agente.",
                ClienteDAO.existeComoAgente(idUsuarioExistente));

        // ID de usuario que no existe en la tabla Agente
        int idUsuarioInexistente = 9999; // Asegúrate de que este ID no exista en la tabla Agente
        assertFalse("El método debería devolver false para un usuario inexistente en la tabla Agente.",
                ClienteDAO.existeComoAgente(idUsuarioInexistente));
    }
}

package exceptions;
// Excepción personalizada para errores de validación.
// Permite lanzar una excepción con un mensaje específico.
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

}

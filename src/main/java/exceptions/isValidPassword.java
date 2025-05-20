package exceptions;

public class isValidPassword extends RuntimeException {
    public isValidPassword(String message) {
        super(message);
    }
}

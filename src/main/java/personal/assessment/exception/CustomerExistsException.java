package personal.assessment.exception;

public class CustomerExistsException extends RuntimeException {
    public CustomerExistsException(String message) {
        super(message);
    }
}

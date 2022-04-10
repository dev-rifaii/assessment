package personal.assessment.exception;

public class NonExistentCustomerException extends RuntimeException {
    public NonExistentCustomerException(String message) {
        super(message);
    }
}

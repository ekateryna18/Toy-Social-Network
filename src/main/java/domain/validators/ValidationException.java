package domain.validators;

/**
 * exception class for validating
 */
public class ValidationException extends RuntimeException{
    /**
     * constructor
     */
    public ValidationException() {
    }

    /**
     * function for exception
     * @param message message of the exception thrown
     */
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

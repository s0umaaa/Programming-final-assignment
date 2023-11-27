/**
 * Exception thrown when unknown entity is encountered.
 *
 * @author Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
public class UnknownEntityException extends Exception {

    /**
     * Constructs the exception with given message.
     *
     * @param message Error message
     */
    public UnknownEntityException(String message) {
        super(message);
    }
}

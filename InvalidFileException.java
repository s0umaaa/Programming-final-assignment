/**
 * Exception thrown when invalid file is encountered.
 *
 * @author Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
public class InvalidFileException extends Exception {

    /**
     * Constructs the exception with given message.
     * 
     * @param message Error message
     */
    public InvalidFileException(String message) {
        super(message);
    }
}

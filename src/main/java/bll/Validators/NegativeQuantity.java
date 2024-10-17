package bll.Validators;

/**
 * The NegativeQuantity class represents an exception that is thrown when a negative quantity is encountered.
 * It extends the Exception class.
 */
public class NegativeQuantity extends Exception{
    public NegativeQuantity(String message) {
        super(message);
    }
}

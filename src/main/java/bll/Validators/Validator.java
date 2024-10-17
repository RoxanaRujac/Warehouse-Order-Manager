package bll.Validators;

/**
 * The Validator interface defines a contract for validation operations.
 * Implementing classes should provide a method to validate a specific type of data.
 *
 * @param <T> the type of data to be validated
 */

public interface Validator<T> {

    public void validate(T t) throws NegativeQuantity;
}

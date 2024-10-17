package bll.Validators;

/**
 * The QuantityValidator class is responsible for validating if a quantity is positive.
 * It implements the Validator interface for Integer values.
 */
public class QuantityValidator extends Throwable implements Validator<Integer> {

    @Override
    public void validate(Integer quantity) throws NegativeQuantity {
        if (quantity <= 0) {
            throw new NegativeQuantity("Negative quantity");
        }
    }

}

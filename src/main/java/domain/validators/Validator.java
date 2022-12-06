package domain.validators;

/**
 * generic validator class
 * @param <T> the entity which needs to be validated
 */
public interface Validator<T> {
    /**
     * validate function for the entity
     * @param entity the entity which is validated
     * @throws ValidationException the exceptions after the validation
     */
    void validate(T entity) throws ValidationException;
}

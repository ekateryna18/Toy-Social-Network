package domain.validators;

/**
 * Abstract class for validator
 * @param <E> the entity to be validated
 */
public abstract class AbstractValidator<E> implements Validator<E>
{

    @Override
    public abstract void validate(E entity) throws ValidationException;
}

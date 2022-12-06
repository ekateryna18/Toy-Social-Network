package domain.validators;

import domain.Utilizator;

/**
 * validator for user
 */
//public class UtilizatorValidator extends AbstractValidator<Utilizator> {
public class UtilizatorValidator implements Validator<Utilizator> {
    /**
     * constructor
     */
    public UtilizatorValidator() {
    }

    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String errMsg="";
        String firstname = entity.getFirstName();
        String lastname = entity.getLastName();
        if(entity.getId() == null)
            errMsg+= "Id cannot be null!\n";
        if(firstname.length() <=2)
            errMsg+= "First name must have at least 3 letters\n";
        if(!firstname.matches("^[A-Z][a-z]+$"))
            errMsg+= "First name must start with capital letter and contain just letters\n";
        if(lastname.length() <=2)
            errMsg+= "Last name must have at least 3 letters\n";
        if(!lastname.matches("^[A-Z][a-z]+$"))
            errMsg+= "Last name must start with capital letter and contain just letters\n";
        if(!errMsg.equals(""))
            throw new ValidationException(errMsg);
    }
}

package domain.validators;

import domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {

    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String errMsg="";
        if(entity.getId() == null)
            errMsg+= "Id cannot be null!\n";

        if(entity.getFirstName().length() <=2)
            errMsg+= "First name must have at least 3 letters\n";
        if(!entity.getFirstName().matches("^[A-Z][a-z]+$"))
            errMsg+= "First name must start with capital letter and contain just letters\n";

        if(entity.getLastName().length() <=2)
            errMsg+= "Last name must have at least 3 letters\n";
        if(!entity.getLastName().matches("^[A-Z][a-z]+$"))
            errMsg+= "Last name must start with capital letter and contain just letters\n";
        if(errMsg != "")
            throw new ValidationException(errMsg);
    }
}

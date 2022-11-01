package domain.validators;

import domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {

    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String errMsg="";
        if(entity.getId() == null)
            errMsg+= "Id cannot fi null!";

        if(entity.getFirstName().length() <=1)
            errMsg+= "First name must have at least 2 letters";
        if(!entity.getFirstName().matches("^[A-Z][a-z]+$"))
            errMsg+= "First name must start with capital letter and contain just letters";

        if(entity.getLastName().length() <=1)
            errMsg+= "Last name must have at least 2 letters";
        if(!entity.getLastName().matches("^[A-Z][a-z]+$"))
            errMsg+= "Last name must start with capital letter and contain just letters";
        if(errMsg != "")
            throw new ValidationException(errMsg);
    }
}

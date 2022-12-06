package domain.validators;

import domain.Friendship;

/**
 * validator for friendship class
 */
//public class FriendshipValidator extends AbstractValidator<Friendship>{
public class FriendshipValidator implements Validator<Friendship>{
    /**
     * constructor for FriendshipValidator class
     */
    public FriendshipValidator() {
    }

    @Override
    public void validate(Friendship entity) throws ValidationException {
        String errMsg="";
        if(entity.getId() == null)
            errMsg+= "Id cannot be null!\n";
        if(!errMsg.equals(""))
            throw new ValidationException(errMsg);
    }
}

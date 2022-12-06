package domain.validators;

public class ValidatorFactory {
    public ValidatorFactory() {
    }

    /*public AbstractValidator createValidator(ValidatorStrategy strategy) {
        if (strategy == ValidatorStrategy.User)
            return new UtilizatorValidator();
        else
            if(strategy == ValidatorStrategy.Friendship)
            return new FriendshipValidator();
        return null;

    }*/

    /*private static ValidatorFactory instance = null;
    public static ValidatorFactory getInstance() {
        if(instance == null){
            instance = new ValidatorFactory();
        }
        return instance;
    }*/
}

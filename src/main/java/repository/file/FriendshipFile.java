/*
package repository.file;
import domain.Friendship;
import domain.validators.Validator;

import java.util.List;

public class FriendshipFile extends AbstractFileRepository<Long,Friendship> {

    public FriendshipFile(String fileName, Validator<Friendship> validator) {
        super(fileName, validator);
    }

    @Override
    public Friendship extractEntity(List<String> attributes) {
        Friendship friendship = new Friendship(Long.parseLong(attributes.get(1)), Long.parseLong(attributes.get(2)));
        friendship.setId(Long.parseLong(attributes.get(0)));
        return friendship;
    }

    @Override
    protected String createEntityAsString(Friendship entity) {
        return entity.getId() + ";" + entity.getId1() +
                ";" + entity.getId2();
    }
}
*/

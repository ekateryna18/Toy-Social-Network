package repository.memory;

import domain.Entity;
import repository.Repository;

public class MemoFriendshipRepo <ID, E extends Entity<ID>> implements Repository<ID,E> {

    @Override
    public E findOne(ID id) {
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return null;
    }

    @Override
    public E save(E entity) {
        return null;
    }

    @Override
    public E delete(ID id) {
        return null;
    }
}

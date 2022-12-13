/*
package repository.memory;

import domain.Entity;
import domain.validators.AbstractValidator;
import domain.validators.Validator;
import domain.validators.ValidatorFactory;
import domain.validators.ValidatorStrategy;
import repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

*/
/**
 * Repository for in memory persistance
 * @param <ID> the id of the entity
 * @param <E> the entity used for the repository
 *//*

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    //private final AbstractValidator<E> validator;
    private final Validator<E> validator;
    Map<ID,E> entities;
    */
/**
     * constructor of repository
     * @param validator the specific validator
     *//*

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }


    */
/*public InMemoryRepository(ValidatorStrategy strategy) {
        this.validator = ValidatorFactory.getInstance().createValidator(strategy);
        entities=new HashMap<ID,E>();
    }*//*



    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if(entities.get(entity.getId()) != null) {
            return entity;
        }
        else entities.put(entity.getId(),entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        if(entities.containsKey(id)){
            E entity = entities.get(id);
            entities.remove(id);
            return entity;
        }
        return null;
    }
    @Override
    public E update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);

        entities.put(entity.getId(),entity);

        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;

    }
}

*/

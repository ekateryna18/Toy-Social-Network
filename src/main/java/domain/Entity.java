package domain;
//import java.io.Serial;
import java.io.Serializable;

/**
 * generic class for an entity
 * @param <IDtype> the id of the entity
 */
public class Entity<IDtype> implements Serializable{

    private static final long serialVersionUID = 7331115341259248461L;
    /**
     * id of the entity
     */
    private IDtype IDtype;

    /**
     * Constructor of the entity
     */
    public Entity() {}

    /**
     * getter for id
     * @return the id of the entity
     */
    public IDtype getId() {
        return IDtype;
    }

    /**
     * setter for id
     * @param IDtype the new id for the entity
     */
    public void setId(IDtype IDtype) {
        this.IDtype = IDtype;
    }
}

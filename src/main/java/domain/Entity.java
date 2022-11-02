package domain;
import java.io.Serializable;

public class Entity<IDtype> implements Serializable{
    private static final long serialVersionUID = 7331115341259248461L;
    private IDtype IDtype;

    public IDtype getId() {
        return IDtype;
    }

    public void setId(IDtype IDtype) {
        this.IDtype = IDtype;
    }
}

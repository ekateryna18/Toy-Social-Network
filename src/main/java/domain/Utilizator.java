package domain;

import java.util.Objects;

/**
 * user class
 */
public class Utilizator extends Entity<Long>{
    /**
     * first name of the user
     */
    private String firstName;
    /**
     * last name of the user
     */
    private String lastName;

    /**
     *user constructor
     * @param firstName : first name of the user
     * @param lastName : last name of the user
     */
    public Utilizator(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * getter for the first name of the user
     * @return string
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * getter for the last name of the user
     * @return string
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter for the first name
     * @param firstName string
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * setter for the last name
     * @param lastName string
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * override of toString()
     * @return string of the user
     */
    @Override
    public String toString() {
        return "User >> "+
                "  firstName: " + firstName +
                "  lastName:  " + lastName ;

    }

    /**
     * override equals
     * @param o object to be compared with
     * @return boolean value if its equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName())
                && getLastName().equals(that.getLastName());
    }

    /**
     * override of hashcode function
     * @return int of hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

}

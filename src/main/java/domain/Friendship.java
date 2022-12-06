package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Friendship class for friendships between 2 users
 */

public class Friendship extends Entity<Long>{
    /**
     * first id
     */
    private Long id1;
    /**
     * second id
     */
    private Long id2;
    /**
     * date of creating the friendship
     */
    LocalDateTime date;

    /**
     * Constructor for friendship
     * @param id1 id of the first user
     * @param id2 id of the second user
     */
    public Friendship(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
        this.date = LocalDateTime.now();
    }

    public Friendship(Long id1, Long id2, String date){
        this.id1 = id1;
        this.id2 = id2;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = LocalDateTime.parse(date, formatter);
    }
    /**
     * getter for the id of the first user
     * @return first id
     */
    public Long getId1() {
        return id1;
    }

    /**
     * getter for the second id
     * @return second id
     */
    public Long getId2() {
        return id2;
    }

    /**
     * getter for the date of making the relationship
     * @return the date of creating the relationship
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * setter for fisrt id
     * @param id1 new id
     */
    public void setId1(Long id1) {
        this.id1 = id1;
    }
    /**
     * setter for second id
     * @param id2 new id
     */
    public void setId2(Long id2) {
        this.id2 = id2;
    }
    /**
     * setter for date of making the friendship
     * @param date new date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * hashcode from id1 and id2
     * @return int of hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getId1(), this.getId2());
    }

    /**
     * override of the equals function
     * @param obj check if obj is equal with the entity
     * @return boolean value if they are equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Friendship)) return false;
        Friendship that = (Friendship) obj;
        return getId1().equals(that.getId1()) &&
                getId2().equals(that.getId2());
    }

    /**
     * override of function toString
     * @return the string of the entity
     */
    @Override
    public String toString() {
        return "--->" + getId1() + " prieten cu " + getId2() + " din data de "+ getDate();
    }
}

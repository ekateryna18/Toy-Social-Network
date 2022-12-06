package service;

import domain.Friendship;
import domain.Utilizator;

/**
 * interface for the service class
 */
public interface ServiceIn {
    /**
     *service interface
     * @param firstName: first name of the user
     * @param lastName: last name of the user
     */
    public void addUserSrv(String firstName, String lastName);

    /**
     *add friendship
     * @param id1 : id user 1 from friendship
     * @param id2 : id user 2 from friendship
     */
    public void addFrienship(Long id1, Long id2);

    /**
     * delete an user
     * @param id : id of the user that needs to be deleted
     * @throws Exception : if the user with the given id doesn't exist
     */
    public void deleteUserSrv(Long id)throws Exception;

    /**
     * delete a friendship
     * @param id1 : id of user 1 from friendship
     * @param id2: id of user 2 from friendship
     * @throws Exception : if friendship doesn't exist
     */
    public void deleteFriendshipSrv(Long id1, Long id2) throws Exception;

    /**
     * get all the users
     * @return : iterable list of all users
     */
    public Iterable<Utilizator> getAll_Users();

    /**
     * get all the existent friendships
     * @return the iterable list of all friendships
     */
    public Iterable<Friendship> getAll_Friendships();

    /**
     * get the number of conex communities
     * @return number of communities from the social graph
     */
    public int getNoCommunities();

    /**
     * @param id id of the user to be updated
     * @param firstName new first name of the user
     * @param lastName new last name of the user
     */
    public void updateUser(Long id,String firstName, String lastName);

    /**
     *
     * @param id the id of the friendship that is going to be updated
     * @param id1 the id of the first new user
     * @param id2 the id of the new second user
     */
    public void updateFriendship(Long id, Long id1, Long id2);

}

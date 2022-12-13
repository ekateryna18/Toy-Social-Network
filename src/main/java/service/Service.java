package service;
import domain.Friendship;
import domain.Graph;
import domain.Utilizator;
import domain.validators.ValidationException;
import repository.Repository;


import java.util.concurrent.atomic.AtomicLong;

/**
 * the service using repositories for users and friendships
 */
public class Service implements ServiceIn{
    Repository<Long, Utilizator> userRepo;
    Repository<Long, Friendship> friendshipRepo;

    private long IDuser;
    private long IDfr;

    /**
     * constructor in memory
     * @param userRepo the repository for users
     * @param friendshipRepo the repository for friendships
     */
   /* public Service(InMemoryRepository<Long, Utilizator> userRepo, InMemoryRepository<Long, Friendship> friendshipRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
        this.IDuser = loadIDuser();
        this.IDfr = loadIDfr();
    }*/
    public Service(Repository<Long, Utilizator> userRepo, Repository<Long, Friendship> friendshipRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
        this.IDuser = loadIDuser();
        this.IDfr = loadIDfr();
    }
    private long loadIDuser(){
        AtomicLong l = new AtomicLong();
        userRepo.findAll().forEach(x ->{
            if(Long.parseLong(l.toString()) <= x.getId())
                l.set(x.getId()+1);
        });
        return Long.parseLong(l.toString());
    }

    private long loadIDfr(){
        AtomicLong l = new AtomicLong();
        friendshipRepo.findAll().forEach(x ->{
            if(Long.parseLong(l.toString()) <= x.getId())
                l.set(x.getId()+1);
        });
        return Long.parseLong(l.toString());
    }
    @Override
    public void addUserSrv(String firstName, String lastName, String username){
        Utilizator u = new Utilizator(firstName, lastName, username);
        u.setId(IDuser++);
        userRepo.save(u);
    }
    @Override
    public void addFrienship(Long id1, Long id2){
        if(userRepo.findOne(id1) != null && userRepo.findOne(id2) != null)
        {
            Friendship fr = new Friendship(id1, id2);
            fr.setId(IDfr++);
            if(friendshipRepo.save(fr) != null)
            {
                Utilizator u1 = userRepo.findOne(id1);
                Utilizator u2 = userRepo.findOne(id2);
                throw new ValidationException(">>>>>"+ u1.getFirstName()+ " "+ u1.getLastName() + " este deja prieten cu "+
                        u2.getFirstName() + " " + u2.getLastName());
            }
        }
        else
            throw new ValidationException("Error adding friendship!");

    }

    @Override
    public void deleteUserSrv(Long id) throws Exception{
        //delete user
        if(userRepo.delete(id) == null){
            throw new Exception("User with the given ID doesnt exist!");
        }
        else{
            friendshipRepo.findAll().forEach(x->{
                if(x.getId1().equals(id) || x.getId2().equals(id))
                    friendshipRepo.delete(x.getId());

            });
        }
    }

    @Override
    public void deleteFriendshipSrv(Long id1, Long id2) throws Exception{
        if(userRepo.findOne(id1) != null && userRepo.findOne(id2) != null)
        {
            friendshipRepo.findAll().forEach(x->{
                if(x.getId1().equals(id1) && x.getId2().equals(id2)
                        || x.getId1().equals(id2) && x.getId2().equals(id1))
                {
                    friendshipRepo.delete(x.getId());
                }
            });
        }
    }
    @Override
    public Iterable<Utilizator> getAll_Users(){
        return userRepo.findAll();
    }
    @Override
    public Iterable<Friendship> getAll_Friendships(){
        return friendshipRepo.findAll();
    }

    public int getNoCommunities(){
        Graph<Long> graph = new Graph<>();
        friendshipRepo.findAll().forEach(x ->{
            graph.addEdge(x.getId1(), x.getId2());
        });
        graph.DFS();
        return graph.getConnectedComp();
    }

    @Override
    public void updateUser(Long id,String firstName, String lastName) {
        Utilizator u = userRepo.findOne(id);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        userRepo.update(u);
    }

    @Override
    public void updateFriendship(Long id, Long id1, Long id2) {
        Friendship fr = friendshipRepo.findOne(id);
        fr.setId(id1);
        fr.setId2(id2);
        friendshipRepo.update(fr);
    }
}

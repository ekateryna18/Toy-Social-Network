package UI;
import domain.validators.FriendshipValidator;
import domain.validators.UtilizatorValidator;
import repository.database.FriendshipDBRepo;
import repository.database.UserDBRepository;
import service.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * console for running the app with a menu
 */
public class console {
    private final BufferedReader buff;
    private final Service srv;

    /**
     * constructor console
     */
    public console() {
        this.buff = new BufferedReader(new InputStreamReader(System.in));
        //InMemoryRepository<Long,Utilizator> repo = new InMemoryRepository<Long,Utilizator>(validator);
        //InMemoryRepository<Long, Friendship> frRepo = new InMemoryRepository<Long, Friendship>(frValidator);
        //UtilizatorFile userRepo = new UtilizatorFile("src/main/java/data/utilizatori.csv", new UtilizatorValidator());
        //FriendshipFile frRepo = new FriendshipFile("src/main/java/data/friendships.csv", new FriendshipValidator());
        UserDBRepository userRepo = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UtilizatorValidator());
        FriendshipDBRepo frRepo = new FriendshipDBRepo("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new FriendshipValidator());
        this.srv = new Service(userRepo, frRepo);
    }

    private void show_menu(){
        System.out.println("1 - Add user");
        System.out.println("2 - Remove user");
        System.out.println("3 - Show all users");
        System.out.println("4 - Add friend");
        System.out.println("5 - Remove friendship");
        System.out.println("6 - Show all friendships");
        //System.out.println("7 - Show a user's friends");
        System.out.println("8 - Show number of communities");
        System.out.println("9 - Update user");
        System.out.println("10 - Update friendship");
        System.out.println("0 - Exit  menu");
        System.out.println("->->->->->->");
    }

    /**
     * befor launching the app, I added generated some users
     */
    /*public void generate_users(){
        srv.addUserSrv("Ana", "Maria", "anamaria");
        srv.addUserSrv("Anamaria", "Elena", "anaelena");
        srv.addUserSrv("Ana", "Mihaela", "anamiha");
        srv.addUserSrv("Katy", "Munteanu", "katymnt");
        srv.addUserSrv("Mihai", "Popa", "mihpopa");
        srv.addUserSrv("Elena", "Luminita");
        srv.addUserSrv("Katy", "Ioana");

        srv.addFrienship(0L, 1L);
        srv.addFrienship(1L, 2L);
        srv.addFrienship(3L, 6L);
    }*/

    /**
     * adding user from console
     * @throws IOException : if input is wrong
     */
    public void add_User() throws IOException{
        System.out.println("....ADD USER....");
        System.out.println("First name: ");
        String firstName = buff.readLine();
        System.out.println("Last name: ");
        String lastName = buff.readLine();
        System.out.println("username: ");
        String username = buff.readLine();
        srv.addUserSrv(firstName,lastName, username);
        System.out.println(">>>>USER ADDED<<<<<");

    }

    /**
     * read user from console
     * @return users id
     * @throws Exception : if input is wrong
     */
    public Long get_User() throws Exception{
        System.out.println("Type first name: ");
        String name = buff.readLine();
        srv.getAll_Users().forEach(x->{
            if(x.getFirstName().contains(name)){
                System.out.println("ID: "+ x.getId() + " ....First name: "+x.getFirstName() + " ....Last name: "+ x.getLastName());
            }
        });
        System.out.println("Type the ID of the user or type R to try again: ");
        String option = buff.readLine();
        if(option.equals("R")) {
            System.out.println("Retrying...");
            get_User();
        }
        Long ID = Long.parseLong(option);
        System.out.println("You chose user with ID: " + ID);
        return ID;
    }

    /**
     * delete user from console
     * @throws Exception : if input is wrong
     */
    public void delete_User() throws Exception{
        System.out.println("....DELETE USER....");
        Long id = get_User();
        srv.deleteUserSrv(id);
        System.out.println(">>>>USER DELETED<<<<<");

    }

    /**
     * show all the users in console
     */
    public void showAllUsers(){
        srv.getAll_Users().forEach(System.out::println);
    }

    /**
     * add friendship from console
     * @throws Exception : if input is wrong
     */
    public void add_friendship() throws Exception{
        System.out.println("....ADD FRIENDSHIP....");
        System.out.println(">>> User 1 <<<");
        Long id1 = get_User();
        System.out.println(">>> User 2 <<<");
        Long id2  =get_User();
        srv.addFrienship(id1, id2);
        System.out.println("....SUCCES ADDING FRIENDSHIP....");
    }

    /**
     * remove friendship from console
     * @throws Exception : if input is wrong
     */
    public void remove_Friendship() throws Exception {
        System.out.println("....REMOVE FRIENDSHIP....");
        System.out.println(">>> User 1 <<<");
        Long id1 = get_User();
        System.out.println(">>> User 2 <<<");
        Long id2  =get_User();
        srv.deleteFriendshipSrv(id1, id2);
        System.out.println("....SUCCES REMOVING FRIENDSHIP....");
    }

    /**
     * show all friendships
     */
    public void showAll_friendships(){
        srv.getAll_Friendships().forEach(System.out::println);
    }

    /**
     * show the nmber of all conex communities
     */
    public void NoConexComp(){
        int nr = srv.getNoCommunities();
        System.out.println("Number of communities: "+ nr);
    }

    public void update_user()throws Exception{
        System.out.println("....UPDATE USER....");
        System.out.println(">>> FIND USER <<<");
        Long id = get_User();
        System.out.println("NEW First name: ");
        String firstName = buff.readLine();
        System.out.println("NEW Last name: ");
        String lastName = buff.readLine();
        srv.updateUser(id,firstName,lastName);
        System.out.println(">>>>USER UPDATED<<<<<");
    }

    public void update_friendship()throws Exception{
        System.out.println("....UPDATE FRIENDSHIP....");
        System.out.println(">>> CHOOSE FRIENDSHIP <<<");
        showAll_friendships();
        System.out.println(">>> ID of friendship:");
        String option = buff.readLine();
        Long id = Long.parseLong(option);
        System.out.println(">>> User 1 <<<");
        option = buff.readLine();
        Long id1 = Long.parseLong(option);
        System.out.println(">>> User 2 <<<");
        option = buff.readLine();
        Long id2 = Long.parseLong(option);
        srv.updateFriendship(id, id1, id2);
        System.out.println("....SUCCES UPDATING FRIENDSHIP....");
    }

    /**
     * implementing the console and menu
     */
    public void run(){
        //generate_users();

        int option;
        boolean ok = true;
        while(ok){
            try{
                show_menu();
                String line = buff.readLine();
                option = Integer.parseInt(line);
                if(option == 0)
                    {System.out.println("Closing menu...");
                        ok=false;}
                else if(option == 1)
                    add_User();
                else if(option == 2)
                    delete_User();
                else if(option == 3)
                    showAllUsers();
                else if(option == 4)
                    add_friendship();
                else if(option == 5)
                    remove_Friendship();
                else if(option == 6)
                    showAll_friendships();
                else if(option == 8)
                    NoConexComp();
                else if(option == 9)
                    update_user();
                else if(option == 10)
                    update_friendship();
                else
                    System.out.println("Invalid option!");

            }
            catch (IOException e){
                System.out.println("Error reading!");
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid option!");
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

}

package UI;
import domain.Utilizator;
import domain.validators.UtilizatorValidator;
import repository.memory.InMemoryRepository;
import service.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class console {
    private final BufferedReader buff;
    private final Service srv;

    public console() {
        this.buff = new BufferedReader(new InputStreamReader(System.in));
        UtilizatorValidator validator = new UtilizatorValidator();
        InMemoryRepository<Long,Utilizator> repo = new InMemoryRepository<Long,Utilizator>(validator);
        this.srv = new Service(repo);
    }

    private void show_menu(){
        System.out.println("1 - Add user");
        System.out.println("2 - Remove user");
        System.out.println("3 - Show all users");
        System.out.println("4 - Add friend");
        System.out.println("5 - Remove friendship");
        System.out.println("6 - Show all friendships");
        System.out.println("7 - Show a user's friends");
        System.out.println("8 - Show number of comunities");
        System.out.println("0 - Exit  menu");
        System.out.println("->->->->->->");
    }

    public void add_User() throws IOException{
        System.out.println("....ADD USER....");
        System.out.println("First name: ");
        String firstName = buff.readLine();
        System.out.println("Last name: ");
        String lastName = buff.readLine();
        srv.addUserSrv(firstName,lastName);
        System.out.println(">>>>USER ADDED<<<<<");

    }

    public void delete_User() throws Exception{
        System.out.println("....DELETE USER....");
        System.out.println("The first name of the user you want to delete?");
        String name = buff.readLine();
        srv.getAll_Users().forEach(x->{
            if(x.getFirstName().contains(name)){
                System.out.println("ID: "+ x.getId() + " ....First name: "+x.getFirstName() + " ....Last name: "+ x.getLastName());
            }
        });
        System.out.println("Type the ID or type R to try again: ");
        String option = buff.readLine();
        if(option.equals("R")) {
            System.out.println("Retrying...");
            delete_User();
        }
        else {
            Long ID = Long.parseLong(option);
            srv.deleteUserSrv(ID);
            System.out.println(">>>>USER DELETED<<<<<");
        }
    }

    public void showAllUsers(){
        srv.getAll_Users().forEach(System.out::println);
    }

    public void run(){
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

package service;

import domain.Credentials;
import domain.Utilizator;
import repository.Repository;

public class ServiceCredentials{
    Repository<Long, Utilizator> userRepo;
    Repository<Long, Credentials> credRepo;
    private Long id;

    public ServiceCredentials(Repository<Long, Utilizator> userRepo, Repository<Long, Credentials> credRepo) {
        this.userRepo = userRepo;
        this.credRepo = credRepo;
        this.id = findMax();
    }

    public Long findMax(){
        Long i = 0L;
        for(Utilizator u: userRepo.findAll()){
            if(u.getId() > i)
                i = u.getId();
        }
        return i + 1;
    }

    public void registerUser(String firstName, String lastName, String username, String password){
        Utilizator user  = new Utilizator(firstName, lastName, username);
        user.setId(id++);

        userRepo.save(user);
        credRepo.save(new Credentials(username, password, user.getId()));

    }

    public Utilizator loginUser(String username, String password){
        Credentials cr = credRepo.findOneString(username);
        if(cr==null)
            return null;
        String passw = cr.getHashedPassword();
        if(passw.equals(password)){
            System.out.println(username + "logged in");
            return userRepo.findOneString(username);
        }
        return null;
    }
}

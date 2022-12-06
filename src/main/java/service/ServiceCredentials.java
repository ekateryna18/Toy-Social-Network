package service;

import domain.Credentials;
import domain.Utilizator;
import repository.Repository;

public class ServiceCredentials{
    Repository<Long, Utilizator> userRepo;
    Repository<Long, Credentials> credRepo;
    private Long ID;

    public Long findMax(){
        Long i = 0L;
        for(Utilizator u: userRepo.findAll()){
            if(u.getId() > i)
                i = u.getId();
        }
        return i + 1;
    }

    /*public Utilizator registerUser(String firstName, String lastName, String username, String password){

    }*/
}

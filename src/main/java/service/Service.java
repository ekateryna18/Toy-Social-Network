package service;
import domain.Utilizator;
import repository.memory.InMemoryRepository;

import java.util.concurrent.atomic.AtomicLong;

public class Service {
    private final InMemoryRepository<Long, Utilizator> userRepo;
    private long ID;

    public Service(InMemoryRepository<Long, Utilizator> userRepo) {
        this.userRepo = userRepo;
        this.ID = loadID();
    }

    private long loadID(){
        AtomicLong l = new AtomicLong();
        userRepo.findAll().forEach(x ->{
            if(Long.parseLong(l.toString()) <= x.getId())
                l.set(x.getId()+1);
        });
        return Long.parseLong(l.toString());
    }

    public void addUserSrv(String firstName, String lastName){
        Utilizator u = new Utilizator(firstName, lastName);
        u.setId(ID++);
        userRepo.save(u);
    }

    public void deleteUserSrv(Long id) throws Exception{
        if(userRepo.delete(id) == null){
            throw new Exception("User with the given ID doesnt exist!");
        }
    }

    public Iterable<Utilizator> getAll_Users(){
        return userRepo.findAll();
    }


}

package service;

import domain.FriendRequest;
import domain.Friendship;
import domain.Utilizator;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ServiceFriendships {
    Repository<Long, Friendship> friendshipRepo;
    Repository<Long, FriendRequest> requestsRepo;
    private Long id;

    public ServiceFriendships(Repository<Long, Friendship> friendshipRepo, Repository<Long, FriendRequest> requestsRepo) {
        this.friendshipRepo = friendshipRepo;
        this.requestsRepo = requestsRepo;
        this.id = findMax();
    }

    public Long findMax(){
        Long i = 0L;
        for(FriendRequest u: requestsRepo.findAll()){
            if(u.getId() > i)
                i = u.getId();
        }
        return i + 1;
    }

    public String send_request(Long id_s, Long id_r){
        String sql1 = String.format("SELECT * FROM friend_requests WHERE id_sender = %s AND id_receiver = '%s'",id_s, id_r);
        String sql2 = String.format("SELECT * FROM friend_requests WHERE id_sender = %s AND id_receiver = '%s'",id_r, id_s);
        if(requestsRepo.findOneBySQL(sql1)== null && requestsRepo.findOneBySQL(sql2) == null){
            FriendRequest fr = new FriendRequest(id_s, id_r,"pending");
            fr.setId(id++);
            requestsRepo.save(fr);
            return "pending";
        }
        else if(requestsRepo.findOneBySQL(sql1)== null && requestsRepo.findOneBySQL(sql2) != null){
            FriendRequest fr = new FriendRequest(id_r, id_s,"accepted");
            requestsRepo.update(fr);
            FriendRequest req = requestsRepo.findOneBySQL(sql2);
            Long idreq = req.getId();
            Friendship fri = new Friendship(id_s,id_r);
            fri.setId(idreq);
            friendshipRepo.save(fri);
            return "accepted";
        }
        else
            return "sent";
    }
    public FriendRequest getReq(Long id, Long id_receiver){
        String sql = String.format("SELECT * FROM friend_requests WHERE id_sender = %s AND id_receiver = '%s'",id, id_receiver);
        FriendRequest fr = requestsRepo.findOneBySQL(sql);
        return fr;
    }
    public List<FriendRequest> getUserRequests(Long id_receiver){
        List<FriendRequest> pendingFriends = new ArrayList<>();
        for(FriendRequest fr : requestsRepo.findAll()){
            if(fr.getId_receiver().equals(id_receiver)&& fr.getStatus().equals("pending")){
                pendingFriends.add(fr);
            }
        }
        return pendingFriends;
    }

    public void requestResponse(Long id_s, Long id_r,String response){
        String sql1 = String.format("SELECT * FROM friend_requests WHERE id_sender = %s AND id_receiver = '%s'",id_s, id_r);
        FriendRequest fr = requestsRepo.findOneBySQL(sql1);
        fr.setStatus(response);
        requestsRepo.update(fr);
        if(response.equals("accepted")){
            Long idreq = fr.getId();
            Friendship fri = new Friendship(id_s,id_r);
            fri.setId(idreq);
            friendshipRepo.save(fri);
        }
    }

    public void removeReq(Long id_s, Long id_r){
        String sql =String.format("SELECT * FROM friend_requests WHERE id_sender = %s AND id_receiver = '%s'",id_s, id_r);
        String sql1 =String.format("SELECT * FROM friend_requests WHERE id_sender = %s AND id_receiver = '%s'",id_r, id_s);
        FriendRequest fr = requestsRepo.findOneBySQL(sql);
        FriendRequest fr1 = requestsRepo.findOneBySQL(sql1);
        if(fr!=null)
            requestsRepo.delete(fr.getId());
        if(fr1 != null)
            requestsRepo.delete(fr1.getId());
    }
}

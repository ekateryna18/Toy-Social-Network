package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FriendRequest extends Entity<Long>{
    /**
     * id of user who sent the request
     */
    private Long id_sender;
    private Long id;
    private Long id_receiver;
    /**
     * can be "pending" "declined" "accepted"
     */
    private String status;
    private LocalDateTime time_sent;

    public FriendRequest(Long id_sender, Long id_receiver, String status) {
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
        this.status = status;
        this.time_sent = LocalDateTime.now();

    }
    public FriendRequest(Long id1, Long id2, String status,String date){
        this.id_sender = id1;
        this.id_receiver = id2;
        this.status = status;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.time_sent = LocalDateTime.parse(date, formatter);
    }
    public Long getId_sender() {
        return id_sender;
    }

    public void setId_sender(Long id_sender) {
        this.id_sender = id_sender;
    }

    public Long getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(Long id_receiver) {
        this.id_receiver = id_receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTime_sent() {
        return time_sent;
    }

    public void setTime_sent(LocalDateTime time_sent) {
        this.time_sent = time_sent;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}

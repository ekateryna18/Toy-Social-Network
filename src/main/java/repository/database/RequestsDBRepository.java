package repository.database;

import domain.Credentials;
import domain.FriendRequest;
import domain.Friendship;
import repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class RequestsDBRepository implements Repository<Long, FriendRequest> {
    private final String url;
    private final String username;
    private final String password;

    public RequestsDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public FriendRequest findOneBySQL(String sql){
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            if(rs.next()){
                FriendRequest fr = new FriendRequest(
                        rs.getLong("id_sender"),
                        rs.getLong("id_receiver"),
                        rs.getString("status"),
                        rs.getString("time_sent"));
                        fr.setId(rs.getLong("id"));
                return fr;
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

    @Override
    public FriendRequest findOne(Long aLong) {
        String sql = String.format("SELECT * FROM friend_requests WHERE id = '%s'", aLong.toString());
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            rs.next();
            Long id1 = rs.getLong("id_sender");
            Long id2 = rs.getLong("id_receiver");
            String status = rs.getString("status");
            String time = rs.getString("time_sent");

            FriendRequest request = new FriendRequest(id1, id2, status,time);
            request.setId(aLong);
            return request;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FriendRequest findOneString(String str) {
        return null;
    }

    @Override
    public Iterable<FriendRequest> findAll() {
        Set<FriendRequest> requests = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friend_requests");
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long id1 = rs.getLong("id_sender");
                Long id2 = rs.getLong("id_receiver");
                String status = rs.getString("status");
                String time = rs.getString("time_sent");
                FriendRequest request = new FriendRequest(id1, id2, status,time);
                request.setId(id);
                requests.add(request);
            }
            //return fships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public FriendRequest save(FriendRequest entity) {
        String sql = String.format("INSERT INTO friend_requests(id, id_sender, id_receiver, status, time_sent) values ('%s', '%s', '%s', '%s', '%s')",
                entity.getId(), entity.getId_sender(), entity.getId_receiver(),entity.getStatus(), LocalDateTime.now().withSecond(0).withNano(0).toString());
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.executeUpdate();
        }catch (SQLException e) {
            return entity;
        }
        return null;
    }

    @Override
    public FriendRequest delete(Long aLong) {
        FriendRequest req = findOne(aLong);
        if(req == null)
            return null;
        String sql = String.format("DELETE from friend_requests where id = '%s'", aLong.toString());
        try(Connection connection = DriverManager.getConnection(url, username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            return req;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FriendRequest update(FriendRequest entity) {
        String sql = String.format("UPDATE friend_requests SET status = '%s' WHERE id_sender = '%s' AND id_receiver = '%s'",
                entity.getStatus(), entity.getId_sender().toString(), entity.getId_receiver().toString());
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return entity;
        }
    }
}

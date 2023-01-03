package repository.database;

import domain.Friendship;
import domain.Utilizator;
import domain.validators.Validator;
import repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class FriendshipDBRepo implements Repository<Long, Friendship> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Friendship> validator;

    public FriendshipDBRepo(String url, String username, String password, Validator<Friendship> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Friendship findOne(Long aLong) {
        String sql = String.format("SELECT * FROM friendships WHERE id = '%s'", aLong.toString());
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            rs.next();
            Long id1 = rs.getLong("id_u1");
            Long id2 = rs.getLong("id_u2");
            String time = rs.getString("date_time");
            Friendship friendship = new Friendship(id1, id2, time);
            friendship.setId(aLong);
            return friendship;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> fships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long id1 = rs.getLong("id_u1");
                Long id2 = rs.getLong("id_u2");
                String time = rs.getString("date_time");
                Friendship friendship = new Friendship(id1, id2, time);
                friendship.setId(id);
                fships.add(friendship);
            }
            //return fships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fships;
    }

    @Override
    public Friendship save(Friendship entity) {
        validator.validate(entity);
        String sql = String.format("INSERT INTO friendships(id, id_u1, id_u2, date_time) values ('%s', '%s', '%s', '%s')",
                entity.getId(), entity.getId1(), entity.getId2(), LocalDateTime.now().withSecond(0).withNano(0).toString());
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
    public Friendship delete(Long aLong) {
        Friendship frship = findOne(aLong);
        if(frship == null)
            return null;
        String sql = String.format("DELETE from friendships where id = '%s'", aLong.toString());
        try(Connection connection = DriverManager.getConnection(url, username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            return frship;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Friendship update(Friendship entity) {
        validator.validate(entity);
        String sql = String.format("UPDATE friendships SET id_u1 = '%s', id_u2 = '%s' WHERE id = '%s' ",
                entity.getId1(), entity.getId2(), entity.getId().toString());
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return entity;
        }
    }

    @Override
    public Friendship findOneString(String str) {
        return null;
    }

    @Override
    public Friendship findOneBySQL(String Sql) {
        return null;
    }
}

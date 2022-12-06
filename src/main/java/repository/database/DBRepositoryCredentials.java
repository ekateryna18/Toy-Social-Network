package repository.database;

import domain.Credentials;
import domain.Utilizator;
import repository.Repository;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DBRepositoryCredentials implements Repository<Long, Credentials> {
    private String url;
    private String username;
    private String password;

    public DBRepositoryCredentials(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }
    private Credentials findOneBySQL(String sql){
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            if(rs.next()){
                return new Credentials(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getLong("id")
                );
            }
        } catch (SQLException ignored) {
        }
        return null;
    }
    @Override
    public Credentials findOne(Long aLong) throws IllegalArgumentException{
        return findOneBySQL(String.format("SELECT * FROM credentials WHERE id_u = %s", aLong));
    }
    public Credentials findOne(String username) throws IllegalArgumentException {
        return findOneBySQL(String.format("SELECT * FROM credentials WHERE username = '%s'", username));
    }
    @Override
    public Iterable<Credentials> findAll() {
        //return findOneBySQL(String.format("SELECT * FROM credentials"));
        Set<Credentials> creds = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from credentials");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id_u");
                String username = resultSet.getString("username");
                String hashpassword = resultSet.getString("hashed_password");

                Credentials cred = new Credentials(username, hashpassword,id);
                creds.add(cred);
            }
            return creds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creds;
    }

    @Override
    public Credentials save(Credentials entity) {
        String sql = String.format("INSERT INTO credentials(id_u, username, password) values ('%s', '%s', '%s')",
                entity.getId(), entity.getUsername(), entity.getHashedPassword());
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
    public Credentials delete(Long aLong) {
        Credentials cred = findOne(aLong);
        if(cred == null)
            return null;
        String sql = String.format("DELETE from credentials WHERE id_u = '%s'", aLong.toString());
        try(Connection connection = DriverManager.getConnection(url, username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            return cred;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Credentials update(Credentials entity) {
        //no update so far
        return null;
    }
}

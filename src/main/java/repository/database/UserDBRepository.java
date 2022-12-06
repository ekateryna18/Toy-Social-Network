package repository.database;

import domain.Utilizator;
import domain.validators.Validator;
import repository.Repository;

import java.sql.*;
import java.util.HashSet;
//import java.util.Optional;
import java.util.Set;

public class UserDBRepository implements Repository<Long, Utilizator> {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public UserDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Utilizator findOne(Long aLong) {
        String sql = String.format("SELECT * FROM users WHERE id = '%s'", aLong.toString());
        try(Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery()){
            rs.next();
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            Utilizator user = new Utilizator(firstname, lastname);
            user.setId(aLong);
            return user;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilizator save(Utilizator entity) {
        validator.validate(entity);
        String sql = String.format("INSERT INTO users(id, first_name, last_name) values ('%s', '%s', '%s')",
                entity.getId(), entity.getFirstName(), entity.getLastName());
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
    public Utilizator delete(Long aLong) {
        Utilizator user = findOne(aLong);
        if(user == null)
            return null;
        String sql = String.format("DELETE from users where id = '%s'", aLong.toString());
        try(Connection connection = DriverManager.getConnection(url, username,password);
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            return user;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Utilizator update(Utilizator entity) {
        validator.validate(entity);
        String sql = String.format("UPDATE users SET first_name = '%s', last_name = '%s' WHERE id = '%s' ",
                entity.getFirstName(), entity.getLastName(), entity.getId().toString());
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

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public void createUsersTable() {
        String sqlCreate = "create table if not exists user (id BIGINT PRIMARY KEY AUTO_INCREMENT, name varchar(45), lastname varchar(45), age tinyint(3))";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sqlDrop = "drop table if exists user";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlDrop);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String INSERT_NEW = "INSERT INTO user (name, lastname, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных ");
    }

    public void removeUserById(long id) {
        String DELETE = "DELETE FROM user WHERE id = ?";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String GET_ALL = "SELECT * FROM user";
        List<User> list = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
            System.out.println(list);
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

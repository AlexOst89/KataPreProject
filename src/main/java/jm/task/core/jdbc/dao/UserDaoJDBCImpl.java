package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        String createTable = "create table if not exists users (" +
                "id bigint auto_increment, " +
                "name varchar(45) not null, " +
                "lastName varchar(45) not null, " +
                "age int not null, " +
                "primary key (id));";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
            System.out.println("Таблица создана!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось создать таблицу!");
        }
    }

    public void dropUsersTable() throws SQLException {
        String dropTable = "drop table if exists users;";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTable);
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу!");
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String saveUser = "insert into users (name, lastname, age) values (?, ?, ?);";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            System.out.println("Не удалось добавить пользователя!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        String removeUser = "delete from users where id = ?;";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с id " + id + " удален из БД!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить пользователя!");
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String selectAllUsers = "select id, name, lastName, age from users;";

        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAllUsers);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User temp = new User(name, lastName, age);
                temp.setId(id);
                userList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось получить данные из таблицы о пользователях!");
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        String cleanTable = "TRUNCATE TABLE users;";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(cleanTable)){
            preparedStatement.executeUpdate();
            System.out.println("Таблица очищена!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить таблицу!");
        }
    }
}

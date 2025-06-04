package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() throws SQLException {

    }
    Util util = new Util();

    Connection connection = util.getConnection();
    Statement statement = connection.createStatement();

    final String createTable = "create table if not exists users (" +
            "id bigint auto_increment, " +
            "name varchar(45) not null, " +
            "lastName varchar(45) not null, " +
            "age int not null, " +
            "primary key (id));";
    final String dropTable = "drop table if exists users;";
    final String saveUser = "insert into users (name, lastname, age) values (?, ?, ?);";
    final String removeUser = "delete from users where id = ?;";
    final String selectAllUsers = "select id, name, lastName, age from users;";
    final String cleanTable = "TRUNCATE TABLE users;";

    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());


    public void createUsersTable(Connection connection) {

        try {
            statement.executeUpdate(createTable);
            logger.info("Таблица создана!");
        } catch (SQLException e) {
            logger.info("Не удалось создать таблицу!");
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {

        try {
            statement.executeUpdate(dropTable);
            logger.info("Таблица удалена!");
        } catch (SQLException e) {
            logger.info("Не удалось удалить таблицу!");
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            logger.info("Не удалось добавить пользователя!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info("Пользователь с id " + id + " удален из БД!");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("Не удалось удалить пользователя!");
        }
    }

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery(selectAllUsers)) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("Не удалось получить данные из таблицы о пользователях!");
        }
        return userList;
    }

    public void cleanUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(cleanTable)){
            preparedStatement.executeUpdate();
            logger.info("Таблица очищена!");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("Не удалось очистить таблицу!");
        }
    }
}

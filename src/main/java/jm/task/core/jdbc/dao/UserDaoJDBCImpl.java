package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private final Connection connection = new Util().getConnection();

    private static final String CREATE_TABLE = "create table if not exists users (" +
            "id bigint auto_increment, " +
            "name varchar(45) not null, " +
            "lastName varchar(45) not null, " +
            "age int not null, " +
            "primary key (id));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users;";
    private static final String SAVE_USER = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?);";
    private static final String REMOVE_USER = "DELETE FROM users WHERE id = ?;";
    private static final String SELECT_ALL_USERS = "SELECT id, name, lastName, age FROM users;";
    private static final String CLEAN_TABLE = "TRUNCATE TABLE users;";

    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
            LOGGER.info("Таблица создана!");
        } catch (SQLException e) {
            LOGGER.info("Не удалось создать таблицу!");
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE);
            LOGGER.info("Таблица удалена!");
        } catch (SQLException e) {
            LOGGER.info("Не удалось удалить таблицу!");
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            LOGGER.info("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            LOGGER.info("Не удалось добавить пользователя!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Пользователь с id " + id + " удален из БД!");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Не удалось удалить пользователя!");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Не удалось получить данные из таблицы о пользователях!");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE)) {
            preparedStatement.executeUpdate();
            LOGGER.info("Таблица очищена!");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Не удалось очистить таблицу!");
        }
    }
}

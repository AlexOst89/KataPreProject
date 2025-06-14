package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

import static jm.task.core.jdbc.service.UserService.userDao;

public class Main {

    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
//        UserService userService = new UserServiceImpl();
//        userService.createUsersTable();
//        userService.saveUser("Григорий", "Петров", (byte) 20);
//        userService.saveUser("Александр", "Сидоров", (byte) 43);
//        userService.saveUser("Анастасия", "Макаренко", (byte) 32);
//        userService.saveUser("Ярослав", "Жилин", (byte) 57);
//        for (User user: userService.getAllUsers()) {
//            System.out.println(user);
//        }
//        userService.cleanUsersTable();
//        userService.dropUsersTable();

        //UserDao userDao = new UserDaoHibernateImpl();


        userDao.createUsersTable();
        userDao.saveUser("Григорий", "Петров", (byte) 20);
        userDao.saveUser("Александр", "Сидоров", (byte) 43);
        userDao.saveUser("Анастасия", "Макаренко", (byte) 32);
        userDao.saveUser("Ярослав", "Жилин", (byte) 57);
        userDao.removeUserById(2);
        for (User user: userDao.getAllUsers()) {
            System.out.println(user);
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}

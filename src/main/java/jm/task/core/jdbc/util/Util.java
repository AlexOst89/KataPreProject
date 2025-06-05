package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/katapreproject";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            try {
                throw new SQLException("Не удалось загрузить драйвер базы данных", e);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

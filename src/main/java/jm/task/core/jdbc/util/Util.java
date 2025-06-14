package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/katapreproject";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }
    private static final SessionFactory sessionFactory;

    static {
        Configuration config = new Configuration();

        // Базовые свойства
        Properties props = new Properties();
        props.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        props.put(Environment.URL, "jdbc:mysql://localhost:3306/katapreproject");
        props.put(Environment.USER, "admin");
        props.put(Environment.PASS, "admin");
        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        props.put(Environment.SHOW_SQL, "true");
        props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        //props.put(Environment.HBM2DDL_AUTO, "update"); // update - создаёт/обновляет таблицы автоматически

        config.setProperties(props);
        config.addAnnotatedClass(jm.task.core.jdbc.model.User.class); // Регистрация сущности

        StandardServiceRegistry sr = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties())
                .build();

        sessionFactory = config.buildSessionFactory(sr);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}

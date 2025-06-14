package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("create table if not exists users (" +
                    "id bigint auto_increment, " +
                    "name varchar(45) not null, " +
                    "lastName varchar(45) not null, " +
                    "age int not null, " +
                    "primary key (id));").executeUpdate();
            transaction.commit();
            LOGGER.info("Таблица успешно создана.");
        } catch (Exception e) {
            LOGGER.severe("Ошибка создания таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            LOGGER.info("Таблица успешно удалена.");
        } catch (Exception e) {
            LOGGER.severe("Ошибка удаления таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            LOGGER.info("Пользователь '" + name + "' успешно сохранён.");
        } catch (Exception e) {
            LOGGER.severe("Ошибка добавления пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                LOGGER.info("Пользователь с ID=" + id + " успешно удалён.");
            } else {
                LOGGER.warning("Пользователь с данным ID не найден.");
            }
        } catch (Exception e) {
            LOGGER.severe("Ошибка удаления пользователя: " + e.getMessage());
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Ошибка загрузки пользователей: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            LOGGER.info("Все записи из таблицы users были удалены.");
        } catch (Exception e) {
            LOGGER.severe("Ошибка очистки таблицы: " + e.getMessage());
        }
    }
}
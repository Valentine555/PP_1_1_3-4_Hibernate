package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
   //здесь обработка всех искл
   private Connection connection=Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        if (connection == null) {
            System.err.println("Соединение с базой данных не установлено.");
            return;
        }

        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT, " +
                    "name VARCHAR(30), " +
                    "lastName VARCHAR(30), " +
                    "age TINYINT, " +
                    "PRIMARY KEY (id))";
            statement.execute(createTableSQL);
            System.out.println("Таблица успешно создана или уже существует.");
        } catch (SQLException e) {
            System.err.println("Исключение при создании таблицы: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        if (connection == null) {
            System.err.println("Соединение с базой данных не установлено.");
            return;
        }
                try (Statement statement= connection.createStatement()) {
                    statement.execute("DROP TABLE IF EXISTS users");
                    System.out.println("Таблица успешно удалена или не существовала.");
                } catch (SQLException e) {
                System.err.println("Исключение при удалении таблицы: " + e.getMessage());
            }
    }

    public void saveUser(String name, String lastName, byte age) {
        if (connection == null) {
            System.err.println("Соединение с базой данных не установлено.");
            return;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User с именем " + name + " добавлен в базу данных");
            } else {
                System.out.println("Пользователь не был добавлен в базу данных.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        if (connection == null) {
            System.err.println("Соединение с базой данных не установлено.");
            return;
        }
        try(PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM users WHERE id = ?")){
            preparedStatement.setLong(1,id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь с id " + id + " успешно удален.");
            } else {
                System.out.println("Пользователь с id " + id + " не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        if (connection == null) {
            System.err.println("Соединение с базой данных не установлено.");
            return null;
        }
        List<User> users=new ArrayList<>();
        try (Statement statement=connection.createStatement(); ResultSet resultSet=statement.executeQuery("select * from users")){
            while (resultSet.next()){
                User user=new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        if (connection == null) {
            System.err.println("Соединение с базой данных не установлено.");
        }
        try (Statement statement=connection.createStatement()){
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

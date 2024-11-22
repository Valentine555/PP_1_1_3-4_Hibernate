package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URl = "jdbc:mysql://localhost:3306/katapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "springcourse";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(URl,USERNAME,PASSWORD);
            if (connection != null) {
                System.out.println("Соединение с базой данных успешно установлено!");
            } else {
                System.err.println("Не удалось установить соединение с базой данных.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Драйвер JDBC не найден: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void closeConnection(){
        try {
            if (connection!=null){
            connection.close();
            System.out.println("Соединение с базой данных успешно закрыто");
        }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при закрытии соединения с базой данных " +e.getMessage());
        }
    }

}

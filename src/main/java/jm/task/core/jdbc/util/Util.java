package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    //JDBC
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

    //Hibernate
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Создаем конфигурацию Hibernate
            Configuration configuration = new Configuration();

            // Настраиваем свойства Hibernate
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/katapp");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "springcourse");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.show_sql", "true");

            // Добавляем аннотированные классы
            configuration.addAnnotatedClass(User.class);

            // Создаем ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            // Создаем SessionFactory
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

}

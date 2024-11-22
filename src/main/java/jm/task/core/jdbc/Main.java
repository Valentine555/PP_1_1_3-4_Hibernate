package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
       try {
           UserServiceImpl userService=new UserServiceImpl();
           userService.createUsersTable();
           userService.saveUser("Ваня", "Иванов", (byte) 45);
           userService.saveUser("Юлия", "Новикова", (byte) 30);
           userService.saveUser("Аня", "Ильина", (byte) 18);
           userService.saveUser("Таня", "Крапива", (byte) 22);
           System.out.println(userService.getAllUsers());
           userService.cleanUsersTable();
           userService.dropUsersTable();
       } finally {
           Util.closeConnection();
       }
       }

}

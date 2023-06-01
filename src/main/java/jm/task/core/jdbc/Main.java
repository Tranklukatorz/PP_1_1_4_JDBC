package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //
        //   В итоге я не совсем понял где лучше реализовать закрытие соединения с БД.
        //   Мне кажется то, как я реализовал его не верно, тк из кода
        //      вроде бы не совсем ясно где я его открыл.
        //   Если не затруднит, посоветуйте где лучше его реализовывать?
        //
        //   Так же по задаче я не совсем понял как мне обрабатывать исключения
        //


        ArrayList<User> usrs = new ArrayList<>();
        usrs.add(new User("Ирина", "Никитина", (byte) 26));
        usrs.add(new User("Вика", "Потемкина", (byte) 16));
        usrs.add(new User("Игорь", "Герасимов", (byte) 68));
        usrs.add(new User("Богдан", "Титомир", (byte) 35));

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        usrs.forEach(x -> userService.saveUser(x.getName(), x.getLastName(), x.getAge()));

        userService.removeUserById(4);
        userService.removeUserById(1);

        List<User> usrList = userService.getAllUsers();

        System.out.printf("%-4s %-10s %-10s %-5s\n","ИД","Имя","Фамилия","Возраст");
        usrList.forEach(x -> System.out.printf("%-4d %-10s %-10s %-5s\n",
                x.getId(),x.getName(),x.getLastName(), x.getAge()));

        userService.cleanUsersTable();

        System.out.println("---------------------------");

        List<User> usrList2 = userService.getAllUsers();
        usrList2.forEach(x -> System.out.printf("%d %.10s %.7s %.7s\n",
                x.getId(),x.getName(),x.getLastName(), x.getAge()));

        Util.closeConnection();



    }
}

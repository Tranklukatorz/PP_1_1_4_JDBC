package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

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

        for (User user : usrs) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем – %s добавлен в базу данных\n", user.getName());
        }
        System.out.println("\n");
        System.out.printf("%-4s %-8s %-10s %-3s\n","ID","Имя","Фамилия","Возраст" );
        List<User> usrList = userService.getAllUsers();

        usrList.forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeConnection();



    }
}

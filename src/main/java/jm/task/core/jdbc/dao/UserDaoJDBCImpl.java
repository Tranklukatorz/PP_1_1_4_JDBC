package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(Statement stm = Util.getConnection().createStatement()) {
            stm.execute("CREATE TABLE IF NOT EXISTS Users (id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(25) NOT NULL," +
                    "lastName VARCHAR(25) NOT NULL," +
                    "age TINYINT NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement stm = Util.getConnection().createStatement()) {
            stm.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pStm = Util.getConnection().prepareStatement("INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)")) {
            pStm.setString(1, name);
            pStm.setString(2, lastName);
            pStm.setByte(3, age);
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pStm = Util.getConnection().prepareStatement("DELETE FROM Users WHERE id = ?")) {
            pStm.setLong(1, id);
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement stm = Util.getConnection().createStatement(); ResultSet rSet = stm.executeQuery("SELECT * FROM Users")) {
            while (rSet.next()) {
               User tmpUser = new User(rSet.getString("name"),
                       rSet.getString("lastname"),
                       rSet.getByte("age"));
               tmpUser.setId(rSet.getLong("id"));
               users.add(tmpUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement stm = Util.getConnection().createStatement()) {
            stm.executeUpdate("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            //IGNORE
        }
    }
}

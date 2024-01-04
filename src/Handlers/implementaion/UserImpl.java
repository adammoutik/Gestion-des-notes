/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers.implementaion;


import java.lang.Class;
import java.util.ArrayList;
import java.util.List;
import Handlers.Model.*;
import java.sql.*;
import Handlers.interfaces.IUser;
/**
 *
 * @author pc
 */
public class UserImpl implements IUser {
    private String jdbcURL = "jdbc:mysql://localhost:3306/GestionNotes";
    private String jdbcUsername = "adamos";
    private String jdbcPassword = "password";

    private static final String INSERT_USER_SQL = "INSERT INTO User" +
        "  ( first_name, last_name, username, password, role) VALUES " +
        " ( ?, ?, ?, ? ,?);";

    private static final String SELECT_USER_BY_ID = "select user_id, first_name, last_name, username, password, role from User where user_id =?";

    private static final String SELECT_USER_BY_USERNAME = "select user_id, first_name, last_name, role from User where username =?";

    private static final String SELECT_ALL_USERS = "select * from User";
    private static final String DELETE_USER_SQL = "delete from User where user_id = ?;";
    private static final String UPDATE_USER_SQL = "update Users set username = ?, password= ?, role =? where user_id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public int insertUser(User user) throws SQLException {
        try {


            Connection connection = getConnection();
            PreparedStatement preparedStatement =connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirst_name());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

        @Override
    public User findUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String role = rs.getString("role");
                user = new User(id,firstName , lastName, username, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int findUserByUsername(String username) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                return userId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String role = rs.getString("role");
                users.add(new User(id,firstName, lastName, username, password, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public void updateUser(User user) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteUser(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

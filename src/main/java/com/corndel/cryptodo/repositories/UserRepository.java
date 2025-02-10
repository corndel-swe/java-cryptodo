package com.corndel.cryptodo.repositories;

import com.corndel.cryptodo.models.User;
import com.corndel.utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<User> findAll() throws SQLException {

        String query = "SELECT * FROM users";

        try (Connection connection = DB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);) {

            List<User> all = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String createdAt = resultSet.getString("created_at");
                all.add(new User(username, password, email, id, createdAt));
            }

            return all;
        }
    }


    public static User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? ";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                int id = resultSet.getInt("id");
                String user = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String createdAt = resultSet.getString("created_at");
                return new User(user, password, email, id, createdAt);
            }
        }
    }

    public static void create(User user) throws SQLException {
        String query = "INSERT INTO users (username,password,email) VALUES(?,?,?)";

        try (var connection = DB.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, user.username());
            statement.setString(2, user.password());
            statement.setString(3, user.email());

            statement.executeUpdate();
        }
    }
}

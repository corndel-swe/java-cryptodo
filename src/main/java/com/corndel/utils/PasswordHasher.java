package com.corndel.utils;

import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PasswordHasher {
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static void main(String[] args) {

        try {
            List<User> users = UserRepository.findAll();

            for (User user : users) {

                String password = user.password();
                String hashedPassword = hash(password);

                String query = "UPDATE users SET password = ? WHERE id = ?";

                try (Connection connection = DB.getConnection();
                     PreparedStatement statement = connection.prepareStatement(query)) {

                    statement.setString(1, hashedPassword);
                    statement.setInt(2, user.id());

                    int affectedRows = statement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.printf("User %d updated%n", user.id());
                    }

                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}

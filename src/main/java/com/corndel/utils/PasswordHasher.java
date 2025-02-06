package com.corndel.utils;

import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PasswordHasher {

    public static String hash(String password) {
        return new String(BCrypt.hashpw(password, BCrypt.gensalt()).getBytes(), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {

        try {
            List<User> users = UserRepository.findAll();

            // DEBUGGING
            if (!users.getFirst().password().equals("itsAmeA123")) {
                System.out.println("PASSWORDS ALREADY HASHED");
                return;
            }

            for (User user : users) {

                String password = new String(user.password().getBytes(), StandardCharsets.UTF_8);

                String hashedPassword = hash(password);


                byte[] passwordBytes = user.password().getBytes(StandardCharsets.UTF_8);

                // DEBUGGING
                System.out.println(user);
                System.out.println(Arrays.toString(passwordBytes));
                System.out.println(hashedPassword);

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
            e.printStackTrace();
        }

    }
}

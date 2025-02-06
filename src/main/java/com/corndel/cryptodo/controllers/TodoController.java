package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.Todo;
import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.TodoRepository;
import com.corndel.cryptodo.repositories.UserRepository;
import com.corndel.utils.PasswordHasher;
import io.javalin.http.Context;
import io.javalin.security.BasicAuthCredentials;
import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;


public class TodoController {

    public static String extractSalt(String hashedPassword) {
        return hashedPassword.substring(0, 29);
    }

    public static void renderTodos(Context context) {

        BasicAuthCredentials authCredentials = context.basicAuthCredentials();

        try {
            if (authCredentials == null) {
                throw new RuntimeException("No Auth Credentials");
            }

            User user = UserRepository.getUserByUsername(authCredentials.getUsername());

            String givenPasswordUTF = authCredentials.getPassword();
            String storedPasswordUTF = user.password();

            System.out.println("Stored hashed+salted password: " + storedPasswordUTF);
            System.out.println("Given plaintext password: " + givenPasswordUTF);
            String salt = extractSalt(storedPasswordUTF);
            System.out.println("Extracted salt: " + salt);
            System.out.println("Hashed+salted given password: " + BCrypt.hashpw(givenPasswordUTF, salt));


            boolean hasMatch = BCrypt.checkpw(givenPasswordUTF, storedPasswordUTF);

            if (!hasMatch) {
                byte[] passwordBytes = authCredentials.getPassword().getBytes(StandardCharsets.UTF_8);
                String errorMessage = "DB - USER:" +
                        user +
                        "\nAUTH:" +
                        authCredentials +
                        "\nGIVEN PASSWORD BYTES" +
                        Arrays.toString(passwordBytes);

                throw new RuntimeException(errorMessage);
            }

            List<Todo> all = TodoRepository.getTodosByUserId(user.id());

            context.render("./todos/list.html", Map.of("todos", all));

        } catch (Exception e) {
            System.out.println(e.getMessage());

            context.status(401)
                    .header("WWW-Authenticate", "Basic realm=\"cryptodo\"")
                    .result(e.getMessage());
        }
    }
}

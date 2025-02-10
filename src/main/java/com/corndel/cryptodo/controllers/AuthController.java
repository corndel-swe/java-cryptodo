package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.UserRepository;
import io.javalin.http.Context;
import io.javalin.security.BasicAuthCredentials;
import org.mindrot.jbcrypt.BCrypt;

public class AuthController {

    public static void protect(Context context) {
        BasicAuthCredentials authCredentials = context.basicAuthCredentials();

        try {
            if (authCredentials == null) {
                throw new RuntimeException("No Auth Credentials");
            }

            User user = UserRepository.getUserByUsername(authCredentials.getUsername());

            String givenPassword = authCredentials.getPassword();

            String storedPassword = user.password();

            boolean hasMatch = BCrypt.checkpw(givenPassword, storedPassword);

            if (!hasMatch) {
                throw new RuntimeException("Invalid username or password. Please try again.");
            }

        } catch (Exception e) {
            context.status(401)
                    .header("WWW-Authenticate", "Basic realm=\"cryptodo\"")
                    .result(e.getMessage());
        }
    }
}

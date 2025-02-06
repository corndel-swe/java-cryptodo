package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.UserRepository;
import com.corndel.utils.PasswordHasher;
import io.javalin.http.Context;
import io.javalin.security.BasicAuthCredentials;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;


public class UserController {

    public static void create(Context context) {
        String username = context.formParam("username");
        String password = context.formParam("password");
        String email = context.formParam("email");

        if (username == null || password == null || email == null) {
            context.result("Error");
            return;
        }

        String passwordUTF = new String(password.getBytes(), StandardCharsets.UTF_8);

        String hashedPassword = PasswordHasher.hash(passwordUTF);

        // DEBUGGING
        byte[] passwordBytes = passwordUTF.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(passwordBytes));

        User user = new User(username, hashedPassword, email);

        try {
            UserRepository.create(user);
            context.redirect("./todo");
        } catch (SQLException e) {
            context.result("Error");
        }

    }

    public static void register(Context context) {
        context.render("./users/register.html");
    }
}

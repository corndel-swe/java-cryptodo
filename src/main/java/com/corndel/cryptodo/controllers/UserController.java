package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.UserRepository;
import com.corndel.utils.PasswordHasher;
import io.javalin.http.Context;

import java.sql.SQLException;

public class UserController {

    public static void create(Context context) {
        String username = context.formParam("username");
        String password = context.formParam("password");
        String email = context.formParam("email");

        if (username == null || password == null || email == null) {
            context.result("Error");
            return;
        }

        String hashedPassword = PasswordHasher.hash(password);

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

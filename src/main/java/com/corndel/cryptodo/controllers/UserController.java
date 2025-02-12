package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.UserRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class UserController {

    public static void create(Context context) {
        String username = context.formParam("username");
        String password = context.formParam("password");
        String email = context.formParam("email");

        try {

            if (username == null || password == null || email == null) {
                throw new RuntimeException("Please ensure that all required fields  are filled out.");
            }

            User user = new User(username, password, email);

            UserRepository.create(user);
            context.redirect("./todo");
        } catch (Exception e) {
            context.status(HttpStatus.BAD_REQUEST)
                    .result(e.getMessage());
        }

    }

    public static void register(Context context) {
        context.render("./users/register.html");
    }

}

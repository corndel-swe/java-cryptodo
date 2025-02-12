package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.Todo;
import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.TodoRepository;
import com.corndel.cryptodo.repositories.UserRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.security.BasicAuthCredentials;

import java.util.List;
import java.util.Map;


public class TodoController {

    public static void renderTodos(Context context) {

        BasicAuthCredentials authCredentials = context.basicAuthCredentials();

        try {
            if (authCredentials == null) {
                throw new RuntimeException("No Auth Credentials");
            }

            User user = UserRepository.getUserByUsername(authCredentials.getUsername());

            String givenPassword = authCredentials.getPassword();

            String storedPassword = user.password();

            boolean hasMatch = storedPassword.equals(givenPassword);

            if (!hasMatch) {
                throw new RuntimeException("Invalid username or password. Please try again.");
            }

            List<Todo> all = TodoRepository.getTodosByUserId(user.id());

            context.render("./todos/list.html", Map.of("todos", all));

        } catch (Exception e) {
            context.status(HttpStatus.UNAUTHORIZED)
                    .header("WWW-Authenticate", "Basic realm=\"cryptodo\"")
                    .result(e.getMessage());
        }

    }

}

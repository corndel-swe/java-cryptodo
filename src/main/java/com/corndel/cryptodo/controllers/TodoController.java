package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.Todo;
import com.corndel.cryptodo.repositories.TodoRepository;
import com.corndel.cryptodo.repositories.UserRepository;
import io.javalin.http.Context;
import io.javalin.security.BasicAuthCredentials;

import java.util.List;
import java.util.Map;


public class TodoController {

    public static void renderTodos(Context context) {

        BasicAuthCredentials authCredentials = getBasicAuthCredentials(context);

        try {
            if (authCredentials == null || !UserRepository.authenticateUser(authCredentials.getUsername(), authCredentials.getPassword())) {
                throw new RuntimeException("\"Invalid credentials\"");
            }

            List<Todo> all = TodoRepository.getTodosByUsername(authCredentials.getUsername());

            context.render("./todos/list.html", Map.of("todos", all));

        } catch (Exception e) {
            context.status(401)
                    .header("WWW-Authenticate", "Basic realm=\"cryptodo\"")
                    .result(e.getMessage());
        }
    }

    private static BasicAuthCredentials getBasicAuthCredentials(Context context) {
        BasicAuthCredentials authCredentials = context.basicAuthCredentials();

        if (authCredentials == null) {
            authCredentials = context.sessionAttribute("Authorization");
        }

        return authCredentials;
    }
}

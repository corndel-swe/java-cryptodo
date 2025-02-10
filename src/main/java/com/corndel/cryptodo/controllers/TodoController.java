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

    public static void create(Context context) {

        BasicAuthCredentials authCredentials = context.basicAuthCredentials();

        String description = context.formParam("description");

        if (description == null || description.isEmpty()) {
            context.render("./todos/new.html", Map.of("error", true));
            return;
        }

        try {
            User user = UserRepository.getUserByUsername(authCredentials.getUsername());
            TodoRepository.create(new Todo(user.id(), description));
            context.redirect("./todo");
        } catch (Exception e) {
            context.status(HttpStatus.BAD_REQUEST)
                    .result(e.getMessage());
        }

    }

    public static void renderTodos(Context context) {

        BasicAuthCredentials authCredentials = context.basicAuthCredentials();

        try {
            User user = UserRepository.getUserByUsername(authCredentials.getUsername());

            List<Todo> all = TodoRepository.getTodosByUserId(user.id());

            context.render("./todos/list.html", Map.of("todos", all));

        } catch (Exception e) {
            context.status(HttpStatus.BAD_REQUEST)
                    .result(e.getMessage());
        }
    }

    public static void renderCreateTodo(Context context) {
        context.render("./todos/new.html");
    }
}

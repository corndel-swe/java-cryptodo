package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.Todo;
import com.corndel.cryptodo.utils.DB;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TodoController {

    public static void renderTodos(Context context) {



        // HARDCODED TO GET WORKING

        String id = "1";
        String query = "SELECT * FROM todos " +
                "INNER JOIN users ON users.id = todos.user_id " +
                "WHERE users.id = ?";

        try (Connection con = DB.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
        ) {
            stmt.setString(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Todo> all = new ArrayList<>();

                while (resultSet.next()) {
                    all.add(Todo.of(resultSet));
                }

                context.render("./todos/list.html", Map.of("todos", all));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        context.render("./todos/list.html");
    }
}

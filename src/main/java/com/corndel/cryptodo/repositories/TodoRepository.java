package com.corndel.cryptodo.repositories;

import com.corndel.cryptodo.models.Todo;
import com.corndel.utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoRepository {

    public static void create(Todo todo) throws SQLException {
        String query = "INSERT INTO todos (user_id,description,completed) VALUES(?,?,?)";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, todo.userId());
            statement.setString(2, todo.description());
            statement.setBoolean(3, todo.completed());

            statement.executeUpdate();
        }
    }

    public static List<Todo> getTodosByUserId(int userId) throws SQLException {

        String query = "SELECT * FROM todos INNER JOIN users ON users.id = todos.user_id WHERE users.id = ?";

        try (Connection con = DB.getConnection(); PreparedStatement stmt = con.prepareStatement(query)
        ) {
            stmt.setInt(1, userId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Todo> all = new ArrayList<>();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int todoUserId = resultSet.getInt("user_id");
                    String description = resultSet.getString("description");
                    boolean completed = resultSet.getBoolean("completed");
                    all.add(new Todo(id, todoUserId, description, completed));
                }

                return all;
            }
        }

    }
}

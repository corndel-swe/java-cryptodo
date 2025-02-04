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

    public static List<Todo> getTodosByUsername(String username) throws SQLException {

        String query = "SELECT * FROM todos " +
                "INNER JOIN users ON users.id = todos.user_id " +
                "WHERE users.username = ?";

        try (Connection con = DB.getConnection(); PreparedStatement stmt = con.prepareStatement(query)
        ) {
            stmt.setString(1, username);

            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Todo> all = new ArrayList<>();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    boolean completed = resultSet.getBoolean("completed");
                    all.add(new Todo(id, description, completed));
                }

                return all;
            }
        }

    }
}

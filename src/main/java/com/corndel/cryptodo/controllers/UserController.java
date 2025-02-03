package com.corndel.cryptodo.controllers;

import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.utils.DB;
import io.javalin.http.Context;

import java.sql.SQLException;

public class UserController {

    public static void create(Context context) {
        User user = User.of(context);
        String query = "INSERT INTO users (username,password,email) VALUES(?,?,?)";
        int affectedRows;

        try (var connection = DB.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (affectedRows > 0) {
            context.redirect("/todo");
        } else {
            context.render("");
        }
    }

    public static void register(Context context) {
        context.render("./users/register.html");
    }
}

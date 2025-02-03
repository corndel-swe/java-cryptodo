package com.corndel.cryptodo.utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DB {
    public static final String dbUrl = "jdbc:sqlite:db.sqlite";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }
}

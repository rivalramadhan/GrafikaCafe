package com.grafikacafe.grafikacafe.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {

        public static Connection connection() {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:grafikacafe.db");
                return connection;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }
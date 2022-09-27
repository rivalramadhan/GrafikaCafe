package com.grafikacafe.grafikacafe.Handlers;

import com.grafikacafe.grafikacafe.Models.UserModel;
import com.grafikacafe.grafikacafe.connection.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;

public class HandlerUser {
    PreparedStatement pres = null;
    Connection conn = null;
    ResultSet res = null;

    public void SetUpdateUser(UserModel user) {
        Preferences preferences = Preferences.userRoot();
        preferences.put("id", user.id);
        preferences.put("nama_user", user.username);
        preferences.put("password_user", user.password);
        preferences.put("role", user.role);

    }

    public static UserModel getUpdateUser() {
        Preferences preferences = Preferences.userRoot();
        var id = preferences.get("id", "String");
        var username = preferences.get("username", "String");
        var password = preferences.get("password", "String");
        var role = preferences.get("role", "String");
        return new UserModel(id, username, password, role);
    }

    public boolean Validation(String id, String username) {
        conn = SqliteConnection.connection();
        String validate = "select * from user where id = ? and username = ?";
        String search = "select * from user where id = ?";

        try {
            pres = conn.prepareStatement(validate);
            pres.setString(1, id);
            res = pres.executeQuery();
            if (res.next()) {
                res.close();
                pres.close();
                return false;
            } else {
                res.close();
                pres.close();
                var searching = conn.prepareStatement(search);
                ResultSet searched = null;
                searching.setString(1, id);
                searched = searching.executeQuery();
                if (searched.next()) {
                    searching.close();
                    searched.close();
                    return false;
                } else {
                    searching.close();
                    searched.close();
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        }

    }
}

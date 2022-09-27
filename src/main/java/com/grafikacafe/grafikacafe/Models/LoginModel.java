package com.grafikacafe.grafikacafe.Models;

import com.grafikacafe.grafikacafe.connection.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginModel {

    Connection con = null;
    ResultSet res = null;
    PreparedStatement pres = null;


    public boolean role(String username, String role) {
        con = SqliteConnection.connection();
        String query = "select * from user where username = ? and role = ?";
        try {
            pres = con.prepareStatement(query);
            pres.setString(1, username);
            pres.setString(2, role);
            res = pres.executeQuery();
            if (res.next()) {
                pres.close();
                res.close();
                return true;
            } else {
                pres.close();
                res.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
            return false;
        }
    }

    public boolean Islogin(String usernamefield, String passfield) {
        con = SqliteConnection.connection();
        String query = "select * from user where username = ? and password = ?";
        try {
            pres = con.prepareStatement(query);
            pres.setString(1, usernamefield);
            pres.setString(2, passfield);
            res = pres.executeQuery();
            if (res.next()) {
                pres.close();
                res.close();
                return true;
            } else {
                pres.close();
                res.close();
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getCause());
            return false;

        }
    }

}

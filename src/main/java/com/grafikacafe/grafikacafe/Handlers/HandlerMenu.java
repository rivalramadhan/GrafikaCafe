package com.grafikacafe.grafikacafe.Handlers;

import com.grafikacafe.grafikacafe.Models.MenuModel;
import com.grafikacafe.grafikacafe.Models.UserModel;
import com.grafikacafe.grafikacafe.connection.SqliteConnection;
import javafx.scene.control.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;

public class HandlerMenu {
    PreparedStatement pres = null;
    Connection conn = null;
    ResultSet res = null;

    public void SetUpdateMenu(MenuModel menu) {
        Preferences preferences = Preferences.userRoot();
        preferences.put("id", menu.id);
        preferences.put("namamenu", menu.namamenu);
        preferences.put("harga", menu.harga);
        preferences.put("stok", menu.stok);
        preferences.put("deskripsi", menu.deskripsi);

    }

    public static MenuModel getUpdateMenu() {
        Preferences preferences = Preferences.userRoot();
        var id = preferences.get("id", "String");
        var namamenu = preferences.get("namamenu", "String");
        var harga = preferences.get("harga", "String");
        var stok = preferences.get("stok", "String");
        var deskripsi = preferences.get("deskripsi", "String");
        return new MenuModel(id, namamenu, harga, stok, deskripsi);
    }

    public boolean Validation(String id, String namamenu) {
        conn = SqliteConnection.connection();
        String validate = "select * from menu where id = ? and namamenu = ?";
        String search = "select * from menu where id = ?";

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

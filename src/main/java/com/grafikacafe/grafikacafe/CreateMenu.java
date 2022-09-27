package com.grafikacafe.grafikacafe;

import com.grafikacafe.grafikacafe.Helper.ChangePage;
import com.grafikacafe.grafikacafe.connection.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateMenu extends ChangePage implements Initializable {
    Connection con = null;
    ResultSet res = null;
    PreparedStatement pres = null;


    @FXML
    public TextField namamenu = new TextField();

    @FXML
    public TextField harga = new TextField();

    @FXML
    public TextField stok = new TextField();

    @FXML
    public TextField deskripsi = new TextField();

    @FXML
    public void submit(ActionEvent event) {
        con = SqliteConnection.connection();

        String query = "insert into menu (namamenu, harga, stok, deskripsi) values (?, ?, ?, ?)";
        String validate = "select namamenu from menu where namamenu = ?";
        try {
            if (namamenu.getText().isEmpty() || harga.getText().isEmpty() || stok.getText().isEmpty() || deskripsi.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText((" Form Kosong !! "));
                alert.showAndWait();
            } else {
                var validation = con.prepareStatement(validate);
                validation.setString(1, namamenu.getText());
                res = validation.executeQuery();
                if (res.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText((" Username yang anda masukan telah terdaftar !!"));
                    alert.showAndWait();
                } else {
                    pres = con.prepareStatement(query);
                    pres.setString(1, namamenu.getText());
                    pres.setString(2, harga.getText());
                    pres.setString(3, stok.getText());
                    pres.setString(4, deskripsi.getText());
                    pres.execute();
                    pres.close();
                    con.close();
                    changePage(event, "ListMenu");
                }
                res.close();
                validation.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

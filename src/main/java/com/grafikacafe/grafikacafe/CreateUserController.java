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

public class CreateUserController extends ChangePage implements Initializable {
    Connection con = null;
    ResultSet res = null;
    PreparedStatement pres = null;

    ObservableList<String> role = FXCollections.observableArrayList("Admin", "Manager");

    @FXML
    public TextField c_user = new TextField();

    @FXML
    public TextField c_pass = new TextField();

    @FXML
    public ComboBox<String> pilihrole = new ComboBox<>();

    @FXML
    private void logout(ActionEvent event) {
        changePage(event, "AdminPage");
    }


    @FXML
    private void listuser(ActionEvent event) {
        changePage(event, "AdminPage");
    }

    @FXML
    public void submit(ActionEvent event) {
        con = SqliteConnection.connection();

        String query = "insert into user (username, password, role) values (?, ?, ?)";
        String validate = "select username from user where username = ?";
        try {
            if (c_user.getText().isEmpty() || c_pass.getText().isEmpty() || pilihrole.getValue().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText((" Form Kosong !! "));
                alert.showAndWait();
            } else {
                var validation = con.prepareStatement(validate);
                validation.setString(1, c_user.getText());
                res = validation.executeQuery();
                if (res.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText((" Username yang anda masukan telah terdaftar !!"));
                    alert.showAndWait();
                } else {
                    pres = con.prepareStatement(query);
                    pres.setString(1, c_user.getText());
                    pres.setString(2, c_pass.getText());
                    pres.setString(3, pilihrole.getValue());
                    pres.execute();
                    pres.close();
                    con.close();
                    changePage(event, "AdminPage");
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
        pilihrole.setItems(role);
    }
}



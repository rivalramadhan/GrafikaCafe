package com.grafikacafe.grafikacafe;

import com.grafikacafe.grafikacafe.Handlers.HandlerUser;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditUser extends ChangePage implements Initializable {
    PreparedStatement pres = null;
    Connection conn = null;

    @FXML
    private void back(ActionEvent event) {
        changePage(event, "AdminPage");

    }

    @FXML
    private TextField e_user = new TextField();

    @FXML
    private TextField e_pass = new TextField();

    @FXML
    private ComboBox<String> e_role = new ComboBox<>();

    ObservableList<String> rolelist = FXCollections.observableArrayList("Admin", "Manager");

    public void UpdateQuery() {
        conn = SqliteConnection.connection();
        var handle = HandlerUser.getUpdateUser();
        String query = "update user set username = ?, password = ?, role = ? where id = ?";
        var id = HandlerUser.getUpdateUser();
        try {
            pres = conn.prepareStatement(query);
            pres.setString(1, e_user.getText());
            pres.setString(2, e_pass.getText());
            pres.setString(3, e_role.getValue());
            pres.setString(4, handle.id);
            pres.execute();
            pres.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public void submit(ActionEvent event) {
        var id = HandlerUser.getUpdateUser();
        HandlerUser validation = new HandlerUser();
        try {
            if (e_user.getText().isEmpty() || e_pass.getText().isEmpty() || e_role.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText((" Form Kosong !! "));
                alert.showAndWait();
            } else {
                if (validation.Validation(e_user.getText(), id.id)) {
                    UpdateQuery();
                    changePage(event, "AdminPage");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText((" Username Sudah Terdaftar "));
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void addrole() {
        e_role.setItems(rolelist);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.addrole();
        var data = HandlerUser.getUpdateUser();
        e_user.setText(data.username);
        e_pass.setText(data.password);
        e_role.setValue(data.role);
    }
}

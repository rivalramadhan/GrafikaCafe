package com.grafikacafe.grafikacafe;

import com.grafikacafe.grafikacafe.Helper.ChangePage;
import com.grafikacafe.grafikacafe.Models.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController extends ChangePage implements Initializable {


    @FXML
    private TextField username;

    @FXML
    private PasswordField password = new PasswordField();


    LoginModel loginmodel = new LoginModel();

    public void login(ActionEvent event) {
        try {
            if (loginmodel.Islogin(username.getText(), password.getText())) {
                if (loginmodel.role(username.getText(), "Admin")) {
                    changePage(event, "AdminPage");
                } else {
                    changePage(event, "ListMenu");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(("Username atau Password Salah !!"));
                alert.showAndWait();
            }

        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

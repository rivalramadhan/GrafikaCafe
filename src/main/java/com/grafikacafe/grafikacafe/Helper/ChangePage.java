package com.grafikacafe.grafikacafe.Helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class ChangePage {
    public void changePage(ActionEvent event, String page) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
            stage.setScene(new Scene(root, 1360, 768));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public abstract void initialize(URL location, ResourceBundle resources);
}


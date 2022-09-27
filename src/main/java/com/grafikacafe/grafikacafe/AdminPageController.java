package com.grafikacafe.grafikacafe;

import com.grafikacafe.grafikacafe.Handlers.HandlerUser;
import com.grafikacafe.grafikacafe.Helper.ChangePage;
import com.grafikacafe.grafikacafe.Models.UserModel;
import com.grafikacafe.grafikacafe.connection.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminPageController extends ChangePage implements Initializable {
    UserModel models = null;

    @FXML
    private void buatuser(ActionEvent event) {
        changePage(event, "CreateUser");
    }
    @FXML
    private void logout(ActionEvent event) {
        changePage(event, "Main");
    }


    @FXML
    private TableView<UserModel> usertable;

    @FXML
    private TableColumn<UserModel, String> id;

    @FXML
    private TableColumn<UserModel, String> username;

    @FXML
    private TableColumn<UserModel, String> password;

    @FXML
    private TableColumn<UserModel, String> role;

    @FXML
    private TableColumn<UserModel, String> action;

    ObservableList<UserModel> datalist = FXCollections.observableArrayList();
    PreparedStatement pres = null;
    ResultSet res = null;


    public void Table() {
        RefreshTable();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));


        Callback<TableColumn<UserModel, String>, TableCell<UserModel, String>> cellFactory = (TableColumn<UserModel, String> param) -> {
            final TableCell<UserModel, String> cell = new TableCell<UserModel, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        HBox deleteIcon = new HBox(new Label("Delete"));
                        HBox updateIcon = new HBox(new Label("Edit"));
                        deleteIcon.setStyle(
                                "-fx-cursor : hand"
                        );
                        updateIcon.setStyle(
                                "-fx-cursor : hand"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                models = usertable.getSelectionModel().getSelectedItem();
                                var query = "delete from user where id = ?";
                                var connect = SqliteConnection.connection();
                                pres = connect.prepareStatement(query);
                                pres.setString(1, models.getId());
                                pres.execute();
                                pres.close();
                                RefreshTable();
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                                System.out.println(usertable);
                            }
                        });

                        updateIcon.setOnMouseClicked((MouseEvent event) -> {
                            models = usertable.getSelectionModel().getSelectedItem();
                            try {
                                Main main = new Main();
                                HandlerUser handle = new HandlerUser();
                                UserModel data = new UserModel(models.getId(), models.getUsername(), models.getPassword(), models.getRole());
                                handle.SetUpdateUser(data);
                                main.ChangeScene("EditUser.fxml");
                            } catch (Exception ex) {
                                System.out.println(ex.getCause());

                            }
                        });

                        HBox hBox = new HBox(deleteIcon, updateIcon);
                        hBox.setStyle("-fx-aligment : center");
                        HBox.setMargin(deleteIcon, new Insets(2, 3, 0, 45));
                        HBox.setMargin(updateIcon, new Insets(2, 3, 0, 20));
                        setGraphic(hBox);
                        setText(null);
                    }

                }
            };
            return cell;
        };
        action.setCellFactory(cellFactory);
        usertable.setItems(datalist);
    }

    public void RefreshTable() {
        try {
            datalist.clear();

            var query = "select * from user";
            var connect = SqliteConnection.connection();
            pres = connect.prepareStatement(query);
            res = pres.executeQuery();
            while (res.next()) {
                datalist.add(new UserModel(
                        res.getString("id"),
                        res.getString("username"),
                        res.getString("password"),
                        res.getString("role")
                ));
                usertable.setItems(datalist);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Table();
    }
}

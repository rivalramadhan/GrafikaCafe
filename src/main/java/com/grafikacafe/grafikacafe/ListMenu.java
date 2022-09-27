package com.grafikacafe.grafikacafe;


import com.grafikacafe.grafikacafe.Handlers.HandlerMenu;
import com.grafikacafe.grafikacafe.Helper.ChangePage;
import com.grafikacafe.grafikacafe.Models.MenuModel;
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

public class ListMenu extends ChangePage implements Initializable {
    MenuModel models = null;

    @FXML
    private void logout(ActionEvent event) {
        changePage(event, "Main");
    }
    @FXML
    private void buatmenu(ActionEvent event) {
        changePage(event, "CreateMenu");
    }
    @FXML
    private TableView<MenuModel> menutable;

    @FXML
    private TableColumn<MenuModel, String> id;

    @FXML
    private TableColumn<MenuModel, String> namamenu;

    @FXML
    private TableColumn<MenuModel, String> harga;

    @FXML
    private TableColumn<MenuModel, String> stok;

    @FXML
    private TableColumn<MenuModel, String> deskripsi;

    @FXML
    private TableColumn<MenuModel, String> action;

    ObservableList<MenuModel> datalist = FXCollections.observableArrayList();
    PreparedStatement pres = null;
    ResultSet res = null;


    public void Table() {
        RefreshTable();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        namamenu.setCellValueFactory(new PropertyValueFactory<>("namamenu"));
        harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        stok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        deskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));


        Callback<TableColumn<MenuModel, String>, TableCell<MenuModel, String>> cellFactory = (TableColumn<MenuModel, String> param) -> {
            final TableCell<MenuModel, String> cell = new TableCell<MenuModel, String>() {

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
                                models = menutable.getSelectionModel().getSelectedItem();
                                var query = "delete from menu where id = ?";
                                var connect = SqliteConnection.connection();
                                pres = connect.prepareStatement(query);
                                pres.setString(1, models.getId());
                                pres.execute();
                                pres.close();
                                RefreshTable();
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                                System.out.println(menutable);
                            }
                        });

                        updateIcon.setOnMouseClicked((MouseEvent event) -> {
                            models = menutable.getSelectionModel().getSelectedItem();
                            try {
                                Main main = new Main();
                                HandlerMenu handle = new HandlerMenu();
                                MenuModel data = new MenuModel(models.getId(), models.getNamamenu(), models.getHarga(), models.getStok(), models.getDeskripsi() );
                                handle.SetUpdateMenu(data);
                                main.ChangeScene("CreateMenu.fxml");
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
        menutable.setItems(datalist);
    }

    public void RefreshTable() {
        try {
            datalist.clear();

            var query = "select * from menu";
            var connect = SqliteConnection.connection();
            pres = connect.prepareStatement(query);
            res = pres.executeQuery();
            while (res.next()) {
                datalist.add(new MenuModel(
                        res.getString("id"),
                        res.getString("namamenu"),
                        res.getString("harga"),
                        res.getString("stok"),
                        res.getString("deskripsi")
                ));
                menutable.setItems(datalist);
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


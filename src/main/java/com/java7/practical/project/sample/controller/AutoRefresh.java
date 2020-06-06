package com.java7.practical.project.sample.controller;

import com.java7.practical.project.sample.model.User;
import com.java7.practical.project.sample.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;

public class AutoRefresh extends Thread {

    @FXML
    private final TableView<User> userTableView;

    @FXML
    private final TableColumn<User, String> usernameColumn;

    @FXML
    private final TableColumn<User, String> addressColumn;

    @FXML
    private final TableColumn<User, String> emailColumn;

    @FXML
    private final TableColumn<User, String> firstnameColumn;

    @FXML
    private final TableColumn<User, String> lastnameColumn;

    @FXML
    private final TableColumn<User, Date> dateofbirthColumn;

    private final UserService userService;

    public AutoRefresh(UserService userService, TableView<User> userTableView,
                       TableColumn<User, String> usernameColumn,
                       TableColumn<User, String> addressColumn,
                       TableColumn<User, String> emailColumn,
                       TableColumn<User, String> firstnameColumn,
                       TableColumn<User, String> lastnameColumn,
                       TableColumn<User, Date> dateofbirthColumn) {
        this.userService = userService;
        this.userTableView = userTableView;
        this.usernameColumn = usernameColumn;
        this.addressColumn = addressColumn;
        this.emailColumn = emailColumn;
        this.firstnameColumn = firstnameColumn;
        this.lastnameColumn = lastnameColumn;
        this.dateofbirthColumn = dateofbirthColumn;
    }

    @Override
    public void run() {
       while(true){
           try {
               loadData();
               sleep(10000L);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    private void loadData() {
        List<User> user = userService.getAllUser();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        dateofbirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        ObservableList<User> vetObservableList =
                FXCollections.observableArrayList(user);
        userTableView.setItems(vetObservableList);
    }
}

package com.java7.practical.project.sample.controller;

import com.java7.practical.project.sample.model.User;
import com.java7.practical.project.sample.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;

import static java.lang.Thread.sleep;

public class UserController {
    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> firstnameColumn;

    @FXML
    private TableColumn<User, String> lastnameColumn;

    @FXML
    private TableColumn<User, Date> dateofbirthColumn;

    private UserService userService = new UserService();

    @FXML
    void initialize() {

        AutoRefresh autoRefresh = new AutoRefresh(userService, userTableView,
                usernameColumn, addressColumn, emailColumn, firstnameColumn, lastnameColumn,
                dateofbirthColumn);

        autoRefresh.start();

    }




}

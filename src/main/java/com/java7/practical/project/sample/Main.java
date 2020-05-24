package com.java7.practical.project.sample;

import com.java7.practical.project.sample.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Parent userRegistrationForm = FXMLLoader.load(getClass().getResource("/user_registration.fxml"));
        Scene userRegistrationFormScene = new Scene(userRegistrationForm, 300, 275);
        Scene rootFormScene = new Scene(root, 300, 275);

        Button btn = userRegistrationForm.getChildrenUnmodifiable().stream()
                .filter(t -> "addUserControls".equals(t.getId()))
                .map(t -> (VBox) t)
                .findAny().get().getChildrenUnmodifiable()
                .stream()
                .filter(t -> "addUserBtn".equals(t.getId()))
                .map(t -> (Button) t).findAny().get();

        VBox vBox = userRegistrationForm.getChildrenUnmodifiable().stream()
                .filter(t -> "addUserControls".equals(t.getId()))
                .map(t-> (VBox) t)
                .findAny()
                .get();

        VBox vBoxRoot = root.getChildrenUnmodifiable().stream()
                .filter(t -> "textControlls".equals(t.getId()))
                .map(t-> (VBox) t)
                .findAny()
                .get();

        btn.setOnAction(t -> {
            System.out.println("user insert in progress");
            UserService userService = new UserService();
            String firstName =
                    extractTexfieldFromParent(vBox, "first_name").getText();
            String lastName =
                    extractTexfieldFromParent(vBox, "last_name").getText();
            String address =
                    extractTexfieldFromParent(vBox, "address").getText();
            String userName =
                    extractTexfieldFromParent(vBox, "user_name").getText();
            String email =
                    extractTexfieldFromParent(vBox, "email").getText();

            LocalDate localDate =
                    extractDatePickerFromParent(vBox, "birth_date")
                            .getValue();
            Date date = null;
            if(localDate != null){
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                date = Date.from(instant);
            }

             String result
                     = userService.addUser(userName,firstName,lastName,email,date,address);
             System.out.println(result);



            Text text = extractTextFromParent(vBoxRoot, "addUserResult");
            text.setText(result);
            primaryStage.setScene(rootFormScene);
        });


        primaryStage.setScene(userRegistrationFormScene);
        primaryStage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException,
            SQLException {

        Class.forName("org.h2.Driver");
        Server server = Server.createWebServer().start();
        System.out.println("port: " + server.getPort());
        System.out.println("status: " + server.getStatus());

        launch(args);
    }

    private TextField extractTexfieldFromParent(VBox vBox, String id) {
        return getNodeStream(vBox, id)
                .map(t -> (TextField) t).findFirst().get();
    }

    private DatePicker extractDatePickerFromParent(VBox vBox, String id) {
        return getNodeStream(vBox, id)
                .map(t -> (DatePicker) t).findFirst().get();
    }

    private Text extractTextFromParent(VBox vBox, String id) {
        return getNodeStream(vBox, id)
                .map(t -> (Text) t).findFirst().get();
    }

    private Stream<Node> getNodeStream(VBox vBox, String id) {
        return vBox.getChildren().stream()
                .filter(t -> (t instanceof HBox))
                .map(t -> (HBox) t)
                .flatMap(t -> t.getChildren().stream())
                .filter(t -> id.equals(t.getId()));
    }

}

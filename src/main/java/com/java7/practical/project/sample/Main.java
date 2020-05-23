package com.java7.practical.project.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Hello World !!");

        StackPane stackPane = new StackPane();
        Scene scene2 = new Scene(root, 300, 275);
        Scene scene = new Scene(stackPane, 300, 275);
        primaryStage.setScene(scene);
        Button button = new Button("NAME");
        button.setOnAction(e -> {
            //Open information dialog that says hello
            primaryStage.setScene(scene2);
        });

        Button secondButton = null;
        for(Node node : root.getChildrenUnmodifiable()){
            System.out.println(node.getId());

            if(node.getId().equals("myBox")){
                HBox hBox = (HBox) node;
                for(Node node2 : hBox.getChildren()){
                    if(node2.getId().equals("myButton")){
                        Button button1 = (Button) node2;


                        button1.setOnAction(e -> {
                            //Open information dialog that says hello
             primaryStage.setScene(scene);
                        });
                    }
                }
            }

        }



        stackPane.getChildren().add(button);


        HBox hbox = root.getChildrenUnmodifiable().stream()
                .filter(t-> t.getId().equals("myBox"))
                .map(t->(HBox) t)
                .findAny().get();


        Button myButton = new Button("ME NEW BUTTON!");
        hbox.getChildren().add(myButton);



        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

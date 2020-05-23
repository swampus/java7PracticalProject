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
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("/user_registration.fxml"));

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
        for (Node node : root.getChildrenUnmodifiable()) {
            System.out.println(node.getId());

            if (node.getId().equals("myBox")) {
                HBox hBox = (HBox) node;
                for (Node node2 : hBox.getChildren()) {
                    if (node2.getId().equals("myButton")) {
                        Button button1 = (Button) node2;


                        button1.setOnAction(e -> {
                            //Open information dialog that says hello
                            primaryStage.setScene(scene);
                            Table1 table1 = new Table1();
                            table1.setUser("AAAAAAAAAAAAAAAAAAAAAAA");
                            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
                            HibernateUtil.getSessionFactory().getCurrentSession().save(table1);
                            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

                        });
                    }
                }
            }

        }


        stackPane.getChildren().add(button);


        HBox hbox = root.getChildrenUnmodifiable().stream()
                .filter(t -> t.getId().equals("myBox"))
                .map(t -> (HBox) t)
                .findAny().get();


        Button myButton = new Button("ME NEW BUTTON!");
        hbox.getChildren().add(myButton);


        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            Server server = Server.createWebServer().start();
            System.out.println("port: " + server.getPort());
            System.out.println("status: " + server.getStatus());
            Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa",
                    "password");
            Statement stmt = con.createStatement();
            //stmt.executeUpdate( "DROP TABLE table1" );
            stmt.executeUpdate("CREATE TABLE table1 ( user varchar(50) )");
            stmt.executeUpdate("INSERT INTO table1 ( user ) VALUES ( 'Claudio' )");
            stmt.executeUpdate("INSERT INTO table1 ( user ) VALUES ( 'Bernasconi' )");
            ResultSet rs = stmt.executeQuery("SELECT * FROM table1");
            while (rs.next()) {
                String name = null;

                name = rs.getString("user");
                System.out.println(name);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        launch(args);
    }
}

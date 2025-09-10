package com.example.rosakhutor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.rosakhutor.DbConnector.dbConnect;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

         launch();
        //Tables.createDBclients();
        /*Connection conn = DbConnector.getDbConnect();
        if (conn != null)
        {
            try
            {
                System.out.println("n***** Let terminate the Connection *****");
                conn.close ();
                System.out.println ("Database connection terminated... ");
            }
            catch (Exception ex)
            {
                System.out.println ("Error in connection termination!");
            }
        }*/
        //DbConnector dbConnector = new DbConnector();
        //dbConnector.getEmployeeLoginPasword();

    }
}
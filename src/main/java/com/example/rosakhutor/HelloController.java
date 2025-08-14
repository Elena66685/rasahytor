package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    DbConnector dbConnector = new DbConnector();

    @FXML
    private TextField login;

    @FXML
    private PasswordField pasword;

    @FXML
    private Button entrance;

    @FXML
    private Label label;

    public void OpenEmployeeWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("seller.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)entrance.getScene().getWindow();
        //stage.setTitle("СОТРУДНИКИ");
        stage.setScene(scene);
        stage.show();
    }

    public void admission() throws SQLException, ClassNotFoundException, IOException {
        if(login.getText().equals("") || pasword.getText().equals(""))
        {
            label.setText("Введите логин и пароль");
        }

        else
        {
            ResultSet resultSet = dbConnector.getEmployeeLoginPasword(login.getText(), pasword.getText());
            if (resultSet.next())
            {
                OpenEmployeeWindow();
            }

            else
            {
                label.setText("Пользователя с такими данными нет");
            }

        }
    }

}
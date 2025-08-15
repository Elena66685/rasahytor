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
    SellerController sellerController = new SellerController();

    @FXML
    private TextField login;

    @FXML
    private PasswordField pasword;

    @FXML
    private Button entrance;

    @FXML
    private Label label;


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
                int role = resultSet.getInt(3);
                sellerController.OpenEmployeeWindow(role);
            }

            else
            {
                label.setText("Пользователя с такими данными нет");
            }

        }
    }

}
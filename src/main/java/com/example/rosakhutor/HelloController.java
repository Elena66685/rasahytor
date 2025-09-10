package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.management.relation.Role;
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

    @FXML
    private Button getPassword;

    @FXML
    private Label passwordGetLabel;



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
                GlobalVars.Role = resultSet.getString(3);
                System.out.println(GlobalVars.Role);
                GlobalVars.Images = resultSet.getString(4);
                System.out.println(GlobalVars.Images);
                GlobalVars.Name = resultSet.getString(5);
                System.out.println(GlobalVars.Name);
                sellerController.OpenEmployeeWindow(GlobalVars.Role, GlobalVars.Images, GlobalVars.Name, (Stage) label.getScene().getWindow());
            }

            else
            {
                label.setText("Пользователя с такими данными нет");
            }

        }
    }

    public void getPassword()
    {
        passwordGetLabel.setText(pasword.getText());
        if(pasword.equals("")){
            passwordGetLabel = null;
        }
    }



}
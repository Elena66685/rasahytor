package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.management.relation.Role;
import java.io.IOException;

import static com.example.rosakhutor.GlobalVars.Images;
import static com.example.rosakhutor.GlobalVars.Name;

public class LoginHistoryController {

    SellerController sellerController = new SellerController();

    @FXML
    private Button back;

    public void OpenSellerWindow() throws IOException {
        sellerController.OpenEmployeeWindow(GlobalVars.Role, Images, Name, (Stage) back.getScene().getWindow());
    }
}

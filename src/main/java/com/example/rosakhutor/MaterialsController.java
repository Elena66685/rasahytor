package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.rosakhutor.GlobalVars.*;

public class MaterialsController {

    SellerController sellerController = new SellerController();

    @FXML
    private Button back;

    public void OpenSellerWindow() throws IOException {
        sellerController.OpenEmployeeWindow(Role, Images, Name, (Stage) back.getScene().getWindow());
    }
}

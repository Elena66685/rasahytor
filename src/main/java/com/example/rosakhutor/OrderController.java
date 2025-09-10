package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.management.relation.Role;
import java.awt.*;
import java.io.IOException;

import static com.example.rosakhutor.GlobalVars.*;


public class OrderController {

    SellerController sellerController = new SellerController();

    @FXML
    public Button back;


    public void OpenSellerWindow() throws IOException {
        sellerController.OpenEmployeeWindow(Role, Images, Name, (Stage) back.getScene().getWindow());
    }
}

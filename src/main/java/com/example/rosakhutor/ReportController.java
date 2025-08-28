package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportController {

    @FXML
    private Button back;

    public void OpenSellerWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("seller.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)back.getScene().getWindow();
        stage.setTitle("RosaKhutor");
        stage.setScene(scene);
        stage.show();
    }
}

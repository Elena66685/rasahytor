package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerController {

    @FXML
    private Button back;

    public void OpenHelloWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)back.getScene().getWindow();
        stage.setTitle("СОТРУДНИК_ВЕЩЬ");
        stage.setScene(scene);
        stage.show();
    }
}

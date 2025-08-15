package com.example.rosakhutor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerController {

    static Stage stage;

    @FXML
    public Button back;

    @FXML
    public Button order;

    public void initialize() { // Метод автоматически вызывается после загрузки FXML
        System.out.println("Контроллер загружен");
    }

    public void OpenHelloWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)back.getScene().getWindow();
        stage.setTitle("СОТРУДНИК_ВЕЩЬ");
        stage.setScene(scene);
        stage.show();
    }

    public void OpenEmployeeWindow(int role) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("seller.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Platform.runLater(() -> configureUL(role));
    }

    private static void configureUL(int role)
    {
        var back = stage.getScene().lookup("#back");
        var order = stage.getScene().lookup("#order");

        if (role == 3)
        {
            back.setVisible(false);
        }
        if (role == 1)
        {
            back.setVisible(false);
            order.setDisable(true);
        }

    }


}

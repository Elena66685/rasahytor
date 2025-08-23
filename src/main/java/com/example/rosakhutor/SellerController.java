package com.example.rosakhutor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerController {

    static Stage stage;

    @FXML
    public  ImageView imageView;

    @FXML
    public Button back;

    @FXML
    public Button order;

    @FXML
    public Label name;

    @FXML
    public Label role;

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

    public void OpenEmployeeWindow(String role, String images, String names, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("seller.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        try {
            // Загрузка изображения из ресурса
            ImageView imageView = (ImageView) scene.lookup("#imageView"); // Использовать ID из файла FXML
            Image image = new Image(getClass().getResourceAsStream(images));
            // Установка изображения в ImageView
            imageView.setImage(image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Label name = (Label) scene.lookup("#name");
        name.setText(names);

        Label roles = (Label) scene.lookup("#role");
        roles.setText(role);

        var back = stage.getScene().lookup("#back");
        var order = stage.getScene().lookup("#order");

        if (role.equals("Старший смены"))
        {
            back.setVisible(false);
        }
        if (role.equals("Продавец"))
        {
            back.setVisible(false);
            order.setDisable(true);
        }

    }




}



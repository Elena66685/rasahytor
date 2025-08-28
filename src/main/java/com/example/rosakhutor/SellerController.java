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

    @FXML
    public Button report;

    @FXML
    public Button materials;

    @FXML
    public Button loginHistory;

    @FXML
    public Button product;

    public void initialize() { // Метод автоматически вызывается после загрузки FXML
        System.out.println("Контроллер загружен");

    }

    public void OpenHelloWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)back.getScene().getWindow();
        stage.setTitle("ДОБРО ПОЖАЛОВАТЬ!");
        stage.setScene(scene);
        stage.show();
    }

    public void OpenReportWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("report.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)report.getScene().getWindow();
        stage.setTitle("СФОРМИРОВАТЬ ОТЧЁТ");
        stage.setScene(scene);
        stage.show();
    }

    public void OpenOrderWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("order.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)order.getScene().getWindow();
        stage.setTitle("СФОРМИРОВАТЬ ЗАКАЗ");
        stage.setScene(scene);
        stage.show();
    }

    public void OpenProductWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("product.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)product.getScene().getWindow();
        stage.setTitle("ПРИНЯТЬ ТОВАР");
        stage.setScene(scene);
        stage.show();
    }

    public void OpenMaterialsWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("materials.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)materials.getScene().getWindow();
        stage.setTitle("РАСХОДНЫЕ МАТЕРИАЛЫ");
        stage.setScene(scene);
        stage.show();
    }

    public void OpenLoginHistoryWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginHistory.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)loginHistory.getScene().getWindow();
        stage.setTitle("ИСТОРИЯ ВХОДА");
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
        var report = stage.getScene().lookup("#report");
        var materials = stage.getScene().lookup("#materials");
        var loginHistory = stage.getScene().lookup("#loginHistory");
        var product = stage.getScene().lookup("#product");

        if (role.equals("Старший смены"))
        {
            report.setVisible(false);
            materials.setVisible(false);
            loginHistory.setVisible(false);
        }
        if (role.equals("Продавец"))
        {
            //order.setDisable(true);
            report.setVisible(false);
            materials.setVisible(false);
            loginHistory.setVisible(false);
            product.setVisible(false); 
        }

    }




}



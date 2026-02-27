package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.rosakhutor.GlobalVars.*;

public class ProductController {

    SellerController sellerController = new SellerController();
    DbConnector dbConnector = new DbConnector();

    @FXML
    private Button close;

    @FXML
    private TextField code;

    @FXML
    private Button back;

    public void OpenSellerWindow() throws IOException {
        sellerController.OpenEmployeeWindow(Role, Images, Name, (Stage) back.getScene().getWindow());
    }

    public void CloseOrder() throws SQLException, ClassNotFoundException, IOException {
        String string = code.getText();
        // Получаем текущий момент времени
        /*LocalDateTime now = LocalDateTime.now();
        Timestamp ts = Timestamp.valueOf(now);
        System.out.println(ts.toString());
        String time = ts.toString();*/
        // Получаем текущий момент времени
        LocalDateTime now = LocalDateTime.now();
        // Форматируем
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String time = now.format(formatter);
        System.out.println(time);
        if (!(string.isEmpty())){
            ResultSet resultSet = dbConnector.getOrdersCode(string);
            if (resultSet.next()){
                dbConnector.updateOrderStatus(3, time, string);
                System.out.println("Статус изменен");
                OpenSellerWindow();
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация");
                alert.setHeaderText(null);
                alert.setContentText("Такого заказа нет");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText(null);
            alert.setContentText("Введите код заказа");
            alert.showAndWait();
        }

    }
}

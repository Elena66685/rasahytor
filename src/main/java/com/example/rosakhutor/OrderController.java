package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.management.relation.Role;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.example.rosakhutor.GlobalVars.*;


public class OrderController {

    SellerController sellerController = new SellerController();
    DbConnector dbConnector = new DbConnector();
    int last_id;
    String code;
    String formattedDate;

    @FXML
    public Button back;

    @FXML
    private Button barcode;

    @FXML
    private TextField orderNumber;

    public void initialize() throws SQLException, ClassNotFoundException { // Метод автоматически вызывается после загрузки FXML
        System.out.println("Контроллер загружен");
        OrderNumber();

    }


    public void OpenSellerWindow() throws IOException {
        sellerController.OpenEmployeeWindow(Role, Images, Name, (Stage) back.getScene().getWindow());
    }

    public void OrderNumber() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbConnector.getOrderId();
        System.out.println(resultSet);
        if(resultSet.next()){
            last_id = resultSet.getInt("max");
            last_id = last_id + 1;
            System.out.println(last_id);
            orderNumber.setText(String.valueOf(last_id));
        }
    }

    public void Barcode() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbConnector.getOrderCode();
        System.out.println(resultSet);
        if(resultSet.next()) {
            int code_int = Integer.parseInt(resultSet.getString("cod")) + 1;
            code = String.valueOf(code_int);
            System.out.println(code);
        }
        // Получаем текущий момент времени
        LocalDateTime now = LocalDateTime.now();

        // Извлекаем только дату
        LocalDate dateOnly = now.toLocalDate();

        // Формируем форматированный вывод
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formattedDate = dateOnly.format(formatter);

        System.out.println("Только сегодняшняя дата: " + formattedDate);

        LocalTime time = now.toLocalTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(timeFormatter);
        System.out.println(formattedTime);
        dbConnector.singUpOrders(last_id, code, formattedDate, LocalDateTime.from(time), 45462562, 2, "04.04.2022", 600);

    }
}

package com.example.rosakhutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.management.relation.Role;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.example.rosakhutor.GlobalVars.*;


public class OrderController {

    SellerController sellerController = new SellerController();
    DbConnector dbConnector = new DbConnector();
    int last_id;
    String code;

    @FXML
    public Button back;

    @FXML
    private Button barcode;

    @FXML
    private TextField orderNumber;

    @FXML
    private Slider slider;

    @FXML
    private TextField slidertext;

    public void initialize() throws SQLException, ClassNotFoundException { // Метод автоматически вызывается после загрузки FXML
        System.out.println("Контроллер загружен");
        OrderNumber();
        slidertext.textProperty().bind(slider.valueProperty().asString("%.0f"));

    }


    public void OpenSellerWindow() throws IOException {
        sellerController.OpenEmployeeWindow(Role, Images, Name, (Stage) back.getScene().getWindow());
    }

    public void OrderNumber() throws SQLException, ClassNotFoundException {
        /*ResultSet resultSet = dbConnector.getOrderId();
        System.out.println(resultSet);
        if(resultSet.next()){
            last_id = resultSet.getInt("max");
            last_id = last_id + 1;
            System.out.println(last_id);
            orderNumber.setText(String.valueOf(last_id));
        }*/
        ResultSet resultSet = dbConnector.getOrderCode();
        System.out.println(resultSet);
        if(resultSet.next()) {
            int code_int = Integer.parseInt(resultSet.getString("cod")) + 1;
            code = String.valueOf(code_int);
            System.out.println(code);
            orderNumber.setText(code);
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
        Timestamp ts = Timestamp.valueOf(now);
        System.out.println(ts.toString());
        String result = ts.toString().substring(0, 19);
        System.out.println(result);
        String dataTime = result.replaceAll("[^\\d]", "");
        System.out.println(dataTime);
        Random random = new Random();
        int minValue = 100000;
        int maxValue = 999999;
        int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue;
        String randomStr = String.valueOf(randomNumber);
        System.out.println(randomStr);
        String barcode = code + dataTime + randomStr;
        System.out.println(barcode);

        // Извлекаем только дату
        //LocalDate dateOnly = now.toLocalDate();

        // Формируем форматированный вывод
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //String formattedDate = dateOnly.format(formatter);

        //System.out.println("Только сегодняшняя дата: " + formattedDate);

        //Time time = new Time(System.currentTimeMillis());

        //dbConnector.singUpOrders(code, ts, 45462562, 2, "04.04.2022", 600);

    }
}


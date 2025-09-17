package com.example.rosakhutor;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.management.relation.Role;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.rosakhutor.GlobalVars.Images;
import static com.example.rosakhutor.GlobalVars.Name;

public class LoginHistoryController {

    SellerController sellerController = new SellerController();

    DbConnector dbConnector = new DbConnector();

    ObservableList<Login> lists = FXCollections.observableArrayList();

    @FXML
    private Button back;

    @FXML
    private TableView<Login> tableLogin;


    @FXML
    private TableColumn<Login, Integer> id;

    @FXML
    private TableColumn<Login, String> login;

    @FXML
    private TableColumn<Login, LocalDateTime> dayTime;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        setLoginHistory();
    }

    public void OpenSellerWindow() throws IOException {
        sellerController.OpenEmployeeWindow(GlobalVars.Role, Images, Name, (Stage) back.getScene().getWindow());
    }

    public void setLoginHistory() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbConnector.getLoginHistory();
        while (resultSet.next()) {
            lists.add(new Login(resultSet.getInt("id"), resultSet.getString("login"),
                    resultSet.getObject("last_entry", LocalDateTime.class)));
        }
        System.out.println(lists);
        id.setCellValueFactory(new PropertyValueFactory<Login, Integer>("id"));
        login.setCellValueFactory(new PropertyValueFactory<Login, String>("login"));
        dayTime.setCellValueFactory(new PropertyValueFactory<Login, LocalDateTime>("dayTime"));
        /*dayTime.setCellValueFactory(cellData -> {
            LocalDateTime dt = cellData.getValue().getTime(); // Получаем значение
            return new SimpleStringProperty(dt.toString());
            /*if (dt != null) {
                return new SimpleStringProperty(dt.toString());   // Преобразуем в строку
            } else {
                return new SimpleStringProperty("");
            }*/
       // });


        tableLogin.setItems(lists);

    }


}

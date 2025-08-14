module com.example.rosakhutor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.rosakhutor to javafx.fxml;
    exports com.example.rosakhutor;
}
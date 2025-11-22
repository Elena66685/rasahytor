module com.example.rosakhutor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.management;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires mysql.connector.j;


    opens com.example.rosakhutor to javafx.fxml;
    exports com.example.rosakhutor;
}
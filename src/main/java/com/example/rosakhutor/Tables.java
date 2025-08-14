package com.example.rosakhutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tables {
    public static void createDBclients() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = null;
        Connection connection = DbConnector.getDbConnect();
        try {
            pstmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " +
                    "clients(id integer primary key auto_increment, " +
                    "name text not null," +
                    "code text not null," +
                    "date_of_birth text not null," +
                    "address text not null," +
                    "e_mail text," +
                    "telephone integer);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("таблица создана");
    }

    /*public void inserting_products(String name, Integer price) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = dbConnection.prepareStatement("INSERT INTO products (name, price) values ('"+name+"', "+price+");");
        pstmt.executeUpdate();

    }*/


}

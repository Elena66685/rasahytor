package com.example.rosakhutor;

import java.sql.*;

public class DbConnector {
     public static Connection dbConnect;

     public static Connection getDbConnect() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + Consts.DB_HOST + ":"
                                   + Consts.DB_PORT + "/" + Consts.DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnect = DriverManager.getConnection(connectionString, Consts.DB_USER, Consts.DB_PASS);
        return dbConnect;
    }

    public ResultSet getEmployeeLoginPasword(String login, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = null;
        Connection connection = DbConnector.getDbConnect();
        stmt = connection.prepareStatement("SELECT employee.login, employee.password, role.name, employee.images, employee.name FROM employee, role WHERE login = '"+login+"' AND password = '"+password+"' AND employee.role_id = role.id;");

        ResultSet resultSet = stmt.executeQuery();
        return resultSet;

        /*while (resultSet.next())
         {
            System.out.println(resultSet.getInt (1));
            System.out.println(resultSet.getString (2));
            System.out.println(resultSet.getString (3));
            System.out.println(resultSet.getString (4));
            System.out.println(resultSet.getString(5));
            System.out.println(resultSet.getInt (6));
         }*/
    }

    public ResultSet getLoginHistory() throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = null;
        Connection connection = DbConnector.getDbConnect();
        stmt = connection.prepareStatement("SELECT employee.id, employee.login, employee.last_entry FROM employee;");


        ResultSet resultSet = stmt.executeQuery();
        return resultSet;
    }

    public ResultSet getOrderId() throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = null;
        Connection connection = DbConnector.getDbConnect();
        stmt = connection.prepareStatement("SELECT MAX(id) AS max FROM orders;");


        ResultSet resultSet = stmt.executeQuery();
        return resultSet;
    }

    public ResultSet getOrderCode() throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = null;
        Connection connection = DbConnector.getDbConnect();
        stmt = connection.prepareStatement("SELECT MAX(code) AS cod FROM orders;");


        ResultSet resultSet = stmt.executeQuery();
        return resultSet;
    }

    public void singUpOrders(String code, Timestamp time,
                             Integer clients_id, Integer status_id, String data, Integer times) throws SQLException {

        PreparedStatement pstmt = dbConnect.prepareStatement("INSERT INTO orders (code, time," +
                " clients_id, status_id, closing_date, rental_time) values ('"+code+"', '"+time+"', "+clients_id+", "+status_id+", '"+data+"', "+times+");");
        pstmt.executeUpdate();
    }
}

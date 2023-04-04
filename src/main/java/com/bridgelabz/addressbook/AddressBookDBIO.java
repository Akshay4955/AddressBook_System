package com.bridgelabz.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBIO {

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
        String userName = "root";
        String password = "akshay@11";
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        return connection;
    }

    public List<Contact> readDataFromDB() {
        String sql = "select * from contact inner join address on contact.address_id = address.address_id";
        List<Contact> contactList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                int zipCode = resultSet.getInt("zip");
                long phoneNumber = resultSet.getLong("phone_number");
                String email = resultSet.getString("email");
                contactList.add(new Contact(firstName, lastName, address, city, state, zipCode, phoneNumber, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

}

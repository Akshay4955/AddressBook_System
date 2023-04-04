package com.bridgelabz.addressbook;

import java.sql.*;
import java.time.LocalDate;
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
        List<Contact> contactList = getListOfContacts(sql);
        return contactList;
    }

    public List<Contact> getContactFromDB(String name) {
        String sql = String.format("select * from contact inner join address on contact.address_id = address.address_id where contact.first_name = '%s';",name);
        List<Contact> contactList = getListOfContacts(sql);
        return contactList;
    }

    private List<Contact> getListOfContacts(String sql) {
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

    public int updateContactAddress(String firstName, String email) {
        String sql = String.format("update contact set email = '%s' where first_name = '%s';",email, firstName);
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contact> getContactsForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("select * from contact inner join address on contact.address_id = address.address_id where add_date between '%s' and '%s';",
                                    java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
        List<Contact> contactList = getListOfContacts(sql);
        return contactList;
    }

    public int getNoOfContactsByCity(String city) {
        String sql = String.format("select * from contact inner join address on contact.address_id = address.address_id where city = '%s';", city);
        List<Contact> listOfContacts = getListOfContacts(sql);
        return listOfContacts.size();
    }

    public int getNoOfContactsByState(String state) {
        String sql = String.format("select * from contact inner join address on contact.address_id = address.address_id where state = '%s';", state);
        List<Contact> listOfContacts = getListOfContacts(sql);
        return listOfContacts.size();
    }
}

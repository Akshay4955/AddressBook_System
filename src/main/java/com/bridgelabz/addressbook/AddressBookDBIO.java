package com.bridgelabz.addressbook;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
        String sql = String.format("select * from contact inner join address on contact.address_id = address.address_id where contact.first_name = '%s';", name);
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
        String sql = String.format("update contact set email = '%s' where first_name = '%s';", email, firstName);
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

    public int addNewContact(Contact contact) {
        int addressId = -1;
        Connection connection;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Statement statement = connection.createStatement()) {
            String sql = String.format("insert into address (address, city, state, zip) values " +
                    "('%s', '%s', '%S', '%s');", contact.getAddress(), contact.getCity(), contact.getState(), contact.getZipCode());
            int rowsAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowsAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) addressId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                return 0;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        try (Statement statement = connection.createStatement()) {
            String sql = String.format("insert into contact (first_name, last_name, address_id, phone_number, email, type) values" +
                    "('%s', '%s', '%s', '%s', '%s', 2);", contact.getFirstName(), contact.getLastName(), addressId, contact.getPhoneNumber(), contact.getEmail());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
                return 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addNewContactsFromCSV() {
        Connection connection;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Statement statement = connection.createStatement()) {
            String sql = "create table addressbook_csv (first_name	varchar(15) not null, last_name	varchar(15) not null, address varchar(30) not null," +
                    " city varchar(15) not null, state	varchar(15) not null, zip int not null, phone_number long not null, email varchar(25) not null)";
            statement.execute(sql);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        try (Statement statement = connection.createStatement()) {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/AddressBookContact.csv"));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String sql = String.format("insert into addressbook_csv (first_name, last_name, address, city, state, zip, phone_number, email) values " +
                        "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", nextRecord[3], nextRecord[4], nextRecord[0], nextRecord[1], nextRecord[6], nextRecord[7], nextRecord[5], nextRecord[2]);
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addNewContactsFromJSON() {
        Connection connection;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Statement statement = connection.createStatement()) {
            String sql = "create table addressbook_json (first_name	varchar(15) not null, last_name	varchar(15) not null, address varchar(30) not null," +
                    " city varchar(15) not null, state	varchar(15) not null, zip int not null, phone_number long not null, email varchar(25) not null)";
            statement.execute(sql);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        try (Statement statement = connection.createStatement()) {
            Path filePath = Paths.get("src/main/resources/AddressBookContact.json");
            try (Reader reader = Files.newBufferedReader(filePath)) {
                Gson gson = new Gson();
                List<Contact> contactList = Arrays.asList(gson.fromJson(reader, Contact[].class));
                for (Contact contact : contactList) {
                    String sql = String.format("insert into addressbook_json (first_name, last_name, address, city, state, zip, phone_number, email) values " +
                            "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", contact.getFirstName(), contact.getLastName(), contact.getAddress(), contact.getCity(), contact.getState(), contact.getZipCode(), contact.getPhoneNumber(), contact.getEmail());
                    statement.executeUpdate(sql);
                }
            } catch (IOException | SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteContact(String name) {
        String sql = String.format("delete contact,address from contact inner join address on contact.address_id = address.address_id " +
                                   " where first_name = '%s';",name);
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

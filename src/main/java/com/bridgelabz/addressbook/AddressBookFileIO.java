package com.bridgelabz.addressbook;

import com.bridgelabz.addressbook.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressBookFileIO {
    private static final String FILE_PATH = "src/main/resources/AddressBookContact.txt";

    public void writeDataToFile(Map<String, ArrayList<Contact>> addressBook) {
        File file = new File(FILE_PATH);
        List<Contact> myContactList = addressBook.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (Contact contact : myContactList) {
                bufferedWriter.write(contact.toText());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDataFromFile() {
        File file = new File(FILE_PATH);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            List<Contact> myList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                Contact contact = new Contact();
                for (String str : array) {
                    str = str.trim();
                    String[] data = str.split("=");
                    if (data[0].equalsIgnoreCase("firstName")) {
                        contact.setFirstName(data[1]);
                    } else if (data[0].equalsIgnoreCase("lastName")) {
                        contact.setLastName(data[1]);
                    } else if (data[0].equalsIgnoreCase("address")) {
                        contact.setAddress(data[1]);
                    } else if (data[0].equalsIgnoreCase("city")) {
                        contact.setCity(data[1]);
                    } else if (data[0].equalsIgnoreCase("state")) {
                        contact.setState(data[1]);
                    } else if (data[0].equalsIgnoreCase("zipCode")) {
                        contact.setZipCode(Integer.parseInt(data[1]));
                    } else if (data[0].equalsIgnoreCase("phoneNumber")) {
                        contact.setPhoneNumber(Long.parseLong(data[1]));
                    } else if (data[0].equalsIgnoreCase("email")) {
                        contact.setEmail(data[1]);
                    }
                }
                myList.add(contact);
            }
            System.out.println(myList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

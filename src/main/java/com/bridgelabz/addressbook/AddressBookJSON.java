package com.bridgelabz.addressbook;

import com.bridgelabz.addressbook.Contact;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookJSON {
    private static final String JSON_FILE_PATH = "src/main/resources/AddressBookContact.json";

    public void writeDataToJSON(Map<String, ArrayList<Contact>> addressBook) {
        Path filepath = Paths.get(JSON_FILE_PATH);
        try (FileWriter fileWriter = new FileWriter(String.valueOf(filepath))) {
            List<Contact> myContactList = addressBook.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
            Gson gson = new Gson();
            String json = gson.toJson(myContactList);
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDataFromJSON() {
        Path filePath = Paths.get(JSON_FILE_PATH);
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();
            List<Contact> contactList = Arrays.asList(gson.fromJson(reader, Contact[].class));
            for (Contact contact : contactList) {
                System.out.println(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

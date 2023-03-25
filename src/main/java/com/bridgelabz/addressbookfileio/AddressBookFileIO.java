package com.bridgelabz.addressbookfileio;

import com.bridgelabz.addressbook.Contact;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        for (Contact contact: myContactList) {
            ObjectOutputStream objectOutputStream;
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                objectOutputStream.writeObject(contact);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readDataFromFile() {
        File file = new File(FILE_PATH);
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Contact contact = (Contact) objectInputStream.readObject();
            System.out.println(contact);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

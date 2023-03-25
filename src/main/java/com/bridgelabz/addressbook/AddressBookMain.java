package com.bridgelabz.addressbook;

import com.bridgelabz.addressbookcsv.AddressBookCSV;
import com.bridgelabz.addressbookfileio.AddressBookFileIO;
import com.bridgelabz.addressbookjson.AddressBookJSON;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMain {
    HashMap<String, ArrayList<Contact>> addressBooks = new HashMap<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to AddressBook program....!!!!");
        AddressBookMain addressBookMain = new AddressBookMain();
        AddressBook addressBook = new AddressBook();
        AddressBookFileIO addressBookFileIO = new AddressBookFileIO();
        AddressBookCSV addressBookCSV = new AddressBookCSV();
        AddressBookJSON addressBookJSON = new AddressBookJSON();
        addressBookMain.createAddressBook();
        boolean loop = true;
        while (loop) {
            System.out.println("Enter what you want to perform");
            System.out.println("Press 1 to create new address book" + '\n' + "Press 2 to perform operation on existing address book" +
                    '\n' + "Press 3 to search contacts with city " + '\n' + "Press 4 to get person with city" +
                    '\n' + "Press 5 to get number of contacts by city" + '\n' + "Press 6 to get sorted contacts by name/City/State/Zip" +
                    '\n' +"Press 7 for write to file" + '\n' +"Press 8 for read from file" + '\n' +"Press 9 for write to CSV" +
                    '\n' +"Press 10 for read from CSV" + '\n' +"Press 11 for write to JSON" + '\n' +"Press 12 for read from JSON" +
                    '\n' + "Press 0 to exit");
            final int createAddressBook = 1, operateExisting = 2, searchContacts = 3, getPersonWithCity = 4, getNoOfContactByCity = 5,
                      getSortedContacts = 6, writeToFile = 7, readFromFile = 8, writeToCSV = 9, readFromCSV = 10, writeToJSON = 11,
                      readFromJSON = 12, exit = 0;
            try {
                Scanner input = new Scanner(System.in);
                int option = input.nextInt();
                switch (option) {
                    case createAddressBook:
                        addressBookMain.createAddressBook();
                        break;
                    case operateExisting:
                        System.out.println("Plz enter key belong to address book");
                        String inputKey = input.next().toLowerCase();
                        if (addressBookMain.addressBooks.containsKey(inputKey))
                            addressBook.callAddressBook(addressBookMain.addressBooks.get(inputKey));
                        else
                            System.out.println("Entered key address book not available");
                        break;
                    case searchContacts:
                        addressBookMain.searchContactsWithCity();
                        break;
                    case getPersonWithCity:
                        addressBookMain.getContactByCityAndState();
                        break;
                    case getNoOfContactByCity:
                        addressBookMain.getNumberContacts();
                        break;
                    case getSortedContacts:
                        addressBookMain.getSortedContacts();
                        break;
                    case writeToFile:
                        addressBookFileIO.writeDataToFile(addressBookMain.addressBooks);
                        break;
                    case readFromFile:
                        addressBookFileIO.readDataFromFile();
                        break;
                    case writeToCSV:
                        addressBookCSV.writeDataToCSV(addressBookMain.addressBooks);
                        break;
                    case readFromCSV:
                        addressBookCSV.readDataFromCSV();
                        break;
                    case writeToJSON:
                        addressBookJSON.writeDataToJSON(addressBookMain.addressBooks);
                        break;
                    case readFromJSON:
                        addressBookJSON.readDataFromJSON();
                        break;
                    case exit:
                        loop = false;
                        break;
                    default:
                        System.out.println("Entered Wrong input");
                }
            }catch (InputMismatchException e) {
                System.out.println("You entered wrong input. Please enter valid input");
            }
        }
    }

    public void createAddressBook() {
        System.out.println("Create address book of your choice");
        ArrayList<Contact> contacts = new ArrayList<>();
        System.out.println("Enter unique key name");
        Scanner input = new Scanner(System.in);
        String name = input.next().toLowerCase();
        if (!addressBooks.containsKey(name))
            addressBooks.put(name, contacts);
        else
            System.out.println("Entered key is already available");
    }

    public void searchContactsWithCity() {
        System.out.println("Please enter city name");
        Scanner input = new Scanner(System.in);
        String cityName = input.next();
        List<Contact> listOfContacts = addressBooks.values().stream().flatMap(Collection::stream)
                                       .filter(p -> p.getCity().equalsIgnoreCase(cityName)).collect(Collectors.toList());
        System.out.println(listOfContacts);
    }

    public void getContactByCityAndState() {
        List<Contact> myContactList = addressBooks.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        Map<String, List<Contact>> myContactListByCity = myContactList.stream().collect(Collectors.groupingBy(Contact::getCity));
        System.out.println(myContactListByCity);
        Map<String, List<Contact>> myContactListByState = myContactList.stream().collect(Collectors.groupingBy(Contact::getState));
        System.out.println(myContactListByState);
    }

    public void getNumberContacts() {
        System.out.println("Please enter choice parameter ");
        System.out.println("Press 1 for City" + '\n' + "Press 2 for State");
        final int byCity = 1, byState = 2;
        try {
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch (choice) {
                case byCity:
                    System.out.println("Please enter city name");
                    String cityName = input.next();
                    long countByCity = addressBooks.values().stream().flatMap(Collection::stream).filter(p -> p.getCity().equalsIgnoreCase(cityName)).count();
                    System.out.println("Count of contacts with " + cityName + " are " + countByCity);
                    break;
                case byState:
                    System.out.println("Please enter State name");
                    String stateName = input.next();
                    long countByState = addressBooks.values().stream().flatMap(Collection::stream).filter(p -> p.getCity().equalsIgnoreCase(stateName)).count();
                    System.out.println("Count of contacts with " + stateName + " are " + countByState);
                    break;
                default:
                    System.out.println("You entered wrong input");
            }
        }catch (InputMismatchException e) {
            System.out.println("You entered wrong input. Please enter valid input");
        }

    }

    public void getSortedContacts() {
        System.out.println("Please enter the choice parameter by which you want sort");
        System.out.println("Press 1 for Name" + '\n' + "Press 2 for City" + '\n' + "Press 3 for State" + '\n' + "Press 4 for ZipCode");
        final int byName = 1, byCity = 2, byState = 3, byZipCode = 4;
        try {
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch (choice) {
                case byName:
                    System.out.println(addressBooks.values().stream().flatMap(Collection::stream)
                            .sorted((Comparator.comparing(Contact::getFirstName))).collect(Collectors.toList()));
                    break;
                case byCity:
                    System.out.println(addressBooks.values().stream().flatMap(Collection::stream)
                            .sorted((Comparator.comparing(Contact::getCity))).collect(Collectors.toList()));
                    break;
                case byState:
                    System.out.println(addressBooks.values().stream().flatMap(Collection::stream)
                            .sorted((Comparator.comparing(Contact::getState))).collect(Collectors.toList()));
                    break;
                case byZipCode:
                    System.out.println(addressBooks.values().stream().flatMap(Collection::stream)
                            .sorted((Comparator.comparing(Contact::getZipCode))).collect(Collectors.toList()));
                    break;
                default:
                    System.out.println("You entered wrong input");
            }
        }catch (InputMismatchException e) {
            System.out.println("You entered wrong input. Please enter valid input");
        }
    }
}